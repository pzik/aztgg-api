package com.aztgg.api.company.presentation;

import com.aztgg.api.company.application.dto.GetCompanyCategoriesByCodeResponseDto;
import com.aztgg.api.company.application.dto.GetCompaniesResponseDto;
import com.aztgg.api.company.application.dto.GetStandardCategoriesResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Company", description = "컴패니 API")
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
public interface CompanyApi {

        @Operation(tags = {"Company"}, summary = "컴패니 목록 조회", description = """
            ## API 설명
            Company 목록을 조회합니다.
            """)
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK"),
        })
        GetCompaniesResponseDto getCompanies();

        @Operation(tags = {"Company"}, summary = "컴패니 코드로 카테고리 목록 조회", description = """
            ## API 설명
            CompanyCode로 회사별 카테고리 목록을 조회합니다.
            """)
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK"),
        })
        GetCompanyCategoriesByCodeResponseDto getCompanyCategoriesByCode(String companyCode);

        @Operation(tags = {"Company"}, summary = "정형화 카테고리 목록 조회", description = """
            ## API 설명
            정형화된 카테고리 목록을 조회합니다.
            """)
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK"),
        })
        GetStandardCategoriesResponseDto getStandardCategories();
}
