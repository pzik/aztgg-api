package com.aztgg.api.company.presentation;

import com.aztgg.api.company.application.CompanyService;
import com.aztgg.api.company.application.dto.GetCompanyCategoriesByCodeResponseDto;
import com.aztgg.api.company.application.dto.GetCompaniesResponseDto;
import com.aztgg.api.company.application.dto.GetStandardCategoriesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/companies")
public class CompanyController implements CompanyApi {

    private final CompanyService companyService;

    @Override
    @GetMapping("")
    public GetCompaniesResponseDto getCompanies(@RequestParam(value = "themeCode", required = false) String themeCode) {
        return companyService.getCompanies(themeCode);
    }

    @Override
    @GetMapping("/{companyCode}/categories")
    public GetCompanyCategoriesByCodeResponseDto getCompanyCategoriesByCode(@PathVariable String companyCode) {
        return companyService.getCompanyCategoriesByCode(companyCode);
    }

    @Override
    @GetMapping("/standard-categories")
    public GetStandardCategoriesResponseDto getStandardCategories() {
        return companyService.getStandardCategories();
    }
}
