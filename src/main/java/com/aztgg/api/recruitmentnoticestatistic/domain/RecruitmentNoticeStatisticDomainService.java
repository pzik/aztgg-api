package com.aztgg.api.recruitmentnoticestatistic.domain;

import com.aztgg.api.global.asset.PredefinedCompany;
import com.aztgg.api.global.asset.PredefinedStandardCategory;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentNoticeStatisticDomainService {

    private final RecruitmentNoticeStatisticRepository recruitmentNoticeStatisticRepository;

    public List<RecruitmentNoticeStatistic> findAll(@NonNull LocalDate startAt,
                                                    @NonNull LocalDate endAt,
                                                    @NonNull PredefinedStandardCategory standardCategory,
                                                    @Nullable Collection<PredefinedCompany> companies) {
        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("시작일은 종료일보다 늦을 수 없습니다.");
        }
        if (ChronoUnit.YEARS.between(startAt, endAt) > 1) {
            throw new IllegalArgumentException("최대 1년까지 조회 가능합니다.");
        }

        List<RecruitmentNoticeStatistic> list = recruitmentNoticeStatisticRepository.findAllByCondition(
                startAt,
                endAt,
                standardCategory.name()
        );
        if (companies != null && !companies.isEmpty()) {
            list = list.stream()
                    .filter(a -> companies.contains(PredefinedCompany.fromCode(a.getCompanyCode())))
                    .toList();
        }
        return list;
    }
}
