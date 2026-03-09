package com.aztgg.api.recruitmentnoticestatistic.presentation;

import com.aztgg.api.recruitmentnoticestatistic.application.dto.GetDailyStatisticsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "RecruitmentNoticeStatistic", description = "공고 통계 API")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "BAD_REQUEST",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "500",
                description = "INTERNAL_SERVER_ERROR",
                content = @Content
        ),
})
public interface RecruitmentNoticeStatisticApi {

        @Operation(tags = {"RecruitmentNoticeStatistic"}, summary = "공고 통계 조회", description = """
            ## API 설명
            공고 통계 정보를 조회합니다.
            """)
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK"),
        })
        GetDailyStatisticsResponse getDailyStatistics(LocalDate startAt, LocalDate endAt, String standardCategory, List<String> companyCodes);
}
