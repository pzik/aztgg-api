package com.aztgg.api.recruitmentnotice.presentation;

import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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

    @Operation(tags = {"RecruitmentNotice"}, summary = "RecruitmentNotice로 리다이렉트", description = """
            ## API 설명
            recruitmentNoticeId를 이용해 해당 공고로 리다이렉트를 수행합니다.
            
            # CompanyCode 목록
            * KAKAO
            * WOOWAHAN
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    void redirectByRecruitmentNotice(@Parameter(hidden = true) HttpServletResponse response, Long recruitmentNoticeId) throws IOException;

    @Operation(tags = {"RecruitmentNotice"}, summary = "RecruitmentNotice 목록 조회", description = """
            ## API 설명
            RecruitmentNotice 목록을 조회합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    GetRecruitmentNoticeRedirectionListResponseDto getRecruitmentNoticeRedirectionList(List<String> companyCodes,
                                                                                       int page,
                                                                                       int pageSize);
}
