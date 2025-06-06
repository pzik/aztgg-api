package com.aztgg.api.company.presentation;

import com.aztgg.api.company.application.dto.GetCompanyThemesResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CompanyTheme", description = "컴패니 테마 API")
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
public interface CompanyThemeApi {

        @Operation(tags = {"CompanyTheme"}, summary = "company 테마 목록 조회", description = """
            ## API 설명
            사전 정의된 테마 목록을 조회합니다.
            """)
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK"),
        })
        GetCompanyThemesResponseDto getCompanyThemes();
}
