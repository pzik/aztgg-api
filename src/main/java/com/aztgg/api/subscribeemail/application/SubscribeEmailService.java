package com.aztgg.api.subscribeemail.application;

import com.aztgg.api.global.asset.PredefinedStandardCategory;
import com.aztgg.api.global.exception.CommonErrorCode;
import com.aztgg.api.global.exception.CommonException;
import com.aztgg.api.subscribeemail.application.dto.CreateSubscribeEmailRequestDto;
import com.aztgg.api.subscribeemail.domain.SubscribeEmail;
import com.aztgg.api.subscribeemail.domain.SubscribeEmailCategory;
import com.aztgg.api.subscribeemail.domain.SubscribeEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SubscribeEmailService {

    private final SubscribeEmailRepository subscribeEmailRepository;

    @Transactional
    public void createSubscribeEmail(CreateSubscribeEmailRequestDto payload) {
        // 이미 존재하다면 삭제
        delete(payload.email());

        SubscribeEmail subscribeEmail = SubscribeEmail.builder()
                .email(payload.email())
                .build();

        for (var category : payload.standardCategories()) {
            try {
                PredefinedStandardCategory predefinedStandardCategory = PredefinedStandardCategory.valueOf(category);
                SubscribeEmailCategory subscribeEmailCategory = SubscribeEmailCategory.builder()
                        .category(predefinedStandardCategory.name())
                        .build();

                subscribeEmail.addCategory(subscribeEmailCategory);
            } catch (IllegalArgumentException e) {
                throw new CommonException(CommonErrorCode.BAD_REQUEST, "invalid standardCategory");
            }
        }

        subscribeEmailRepository.save(subscribeEmail);
    }

    @Transactional
    public void delete(String email) {
        SubscribeEmail subscribeEmail = subscribeEmailRepository.findByEmail(email)
                .orElseGet(() -> null);
        if (Objects.isNull(subscribeEmail)) {
            return;
        }

        subscribeEmailRepository.delete(subscribeEmail);
        subscribeEmailRepository.flush();
    }
}
