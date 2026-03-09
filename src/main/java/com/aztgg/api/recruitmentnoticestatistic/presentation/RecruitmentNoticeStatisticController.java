package com.aztgg.api.recruitmentnoticestatistic.presentation;

import com.aztgg.api.recruitmentnoticestatistic.application.RecruitmentNoticeStatisticFacadeService;
import com.aztgg.api.recruitmentnoticestatistic.application.dto.GetDailyStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/recruitment-notice-statistics")
public class RecruitmentNoticeStatisticController implements RecruitmentNoticeStatisticApi {

    private final RecruitmentNoticeStatisticFacadeService recruitmentNoticeStatisticFacadeService;

    @Override
    @GetMapping("/daily")
    public GetDailyStatisticsResponse getDailyStatistics(@RequestParam LocalDate startAt,
                                                         @RequestParam LocalDate endAt,
                                                         @RequestParam String standardCategory,
                                                         @RequestParam(required = false, defaultValue = "") List<String> companyCodes) {
        return recruitmentNoticeStatisticFacadeService.getDailyStatistics(startAt, endAt, standardCategory, companyCodes);
    }
}
