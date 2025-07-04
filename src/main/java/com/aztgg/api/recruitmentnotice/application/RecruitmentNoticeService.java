package com.aztgg.api.recruitmentnotice.application;

import com.aztgg.api.global.exception.CommonErrorCode;
import com.aztgg.api.global.exception.CommonException;
import com.aztgg.api.recruitmentnotice.application.dto.*;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNoticeErrorCode;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitmentNoticeService {

    private final RecruitmentNoticeRepository recruitmentNoticeRepository;

    private static final DateTimeFormatter DAILY_RANK_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public GetRecruitmentNoticeRedirectionResponseDto incrementViewCountAndGetRecruitmentNoticeRedirection(Long recruitmentNoticeId) {
        RecruitmentNotice recruitmentNotice = recruitmentNoticeRepository.findById(recruitmentNoticeId)
                .orElseThrow(() -> new CommonException(RecruitmentNoticeErrorCode.BAD_REQUEST_RECRUITMENT_NOTICE_NOT_FOUND));

        recruitmentNotice.increaseClickCount();
        increaseDailyNoticeClickCount(recruitmentNotice.getRecruitmentNoticeId());
        return GetRecruitmentNoticeRedirectionResponseDto.from(recruitmentNotice);
    }

    @Transactional(readOnly = true)
    public GetRecruitmentNoticeRedirectionListResponseDto getRecruitmentNoticeRedirectionList(GetRecruitmentNoticeRedirectionListRequestDto request) {
        PageRequest pageRequest = PageRequest.of(request.page(), request.pageSize(), Sort.by(Sort.Direction.fromString(request.sort().get(1)), request.sort().get(0)));
        Page<RecruitmentNotice> result = recruitmentNoticeRepository.findByCompanyCodeLikeAndCategoryInAndStandardCategoryLike(request.companyCode(), request.category(), request.standardCategory(), pageRequest);
        return GetRecruitmentNoticeRedirectionListResponseDto.from(result);
    }

    @Transactional(readOnly = true)
    public GetRecruitmentNoticeRedirectionsByRankDto getRecruitmentNoticeRedirectionsByRank(String strDate) {
        try {
            LocalDate.parse(strDate, DAILY_RANK_FORMAT);
            List<Long> ids = recruitmentNoticeRepository.getRecruitmentNoticesByDailyRank(strDate, 100);
            List<RecruitmentNotice> notices = recruitmentNoticeRepository.findAllByRecruitmentNoticeIdIn(ids);

            List<GetRecruitmentNoticeRedirectionResponseDto> result = ids.stream()
                    .map(id -> notices.stream().filter(a -> a.getRecruitmentNoticeId().equals(id)).findFirst())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(GetRecruitmentNoticeRedirectionResponseDto::from)
                    .toList();
            return new GetRecruitmentNoticeRedirectionsByRankDto(result);
        } catch (DateTimeParseException e) {
            throw new CommonException(CommonErrorCode.BAD_REQUEST, "invalid date format");
        }
    }

    @Transactional(readOnly = true)
    public Optional<GetRecruitmentNoticeResponseDto> findRecruitmentNoticeRedirectionById(Long recruitmentNoticeId) {
        return recruitmentNoticeRepository.findById(recruitmentNoticeId)
                .map(GetRecruitmentNoticeResponseDto::from);
    }

    public void incrementClickCountByRecruitmentNoticeId(Long recruitmentNoticeId) {
        RecruitmentNotice recruitmentNotice = recruitmentNoticeRepository.findById(recruitmentNoticeId)
                        .orElseGet(() -> null);
        if (Objects.isNull(recruitmentNotice)) {
            return;
        }
        recruitmentNotice.increaseClickCount();

        // 일일 클릭 랭크 반영
        increaseDailyNoticeClickCount(recruitmentNotice.getRecruitmentNoticeId());
    }

    private void increaseDailyNoticeClickCount(Long recruitmentNoticeId) {
        recruitmentNoticeRepository.increaseDailyNoticeClickCount(recruitmentNoticeId, LocalDateTime.now());
    }
}
