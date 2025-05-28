package com.aztgg.api.recruitmentnotice.presentation;

import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionListResponseDto;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionsByRankDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "RecruitmentNotice", description = "채용공고 API")
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
public interface RecruitmentNoticeApi {

    @Operation(tags = {"RecruitmentNotice"}, summary = "RecruitmentNotice 목록 조회", description = """
            ## API 설명
            RecruitmentNotice 목록을 조회합니다.
            대상 companyCode 내 category에 해당하는 목록을 조회합니다.
            companyCode, cateogry, order 필드는 선택입니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    GetRecruitmentNoticeRedirectionListResponseDto getRecruitmentNoticeRedirectionList(String companyCode,
                                                                                       String category,
                                                                                       String standardCategory,
                                                                                       int page,
                                                                                       int pageSize,
                                                                                       List<String> sort);

    @Operation(tags = {"RecruitmentNotice"}, summary = "RecruitmentNotice 데일리 클릭 랭크 순으로 조회", description = """
            ## API 설명
            RecruitmentNotice 목록을 카운트가 높은 순부터 차례대로 조회합니다. (인기순)
            
            파라미터 포맷은 `yyyy-MM-dd` 입니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    GetRecruitmentNoticeRedirectionsByRankDto getRecruitmentNoticeRedirectionListByRank(String date);


    @Operation(tags = {"RecruitmentNotice"}, summary = "RecruitmentNotice 클릭 카운팅", description = """
            ## API 설명
            RecruitmentNoticeId를 이용해 공고 클릭 카운트 수를 증가시킵니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    void incrementClickCountByRecruitmentNoticeId(Long recruitmentNoticeId);
}
