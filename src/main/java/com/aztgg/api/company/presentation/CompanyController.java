package com.aztgg.api.company.presentation;

import com.aztgg.api.company.application.CompanyService;
import com.aztgg.api.company.application.dto.GetCompanyCategoriesByCodeResponseDto;
import com.aztgg.api.company.application.dto.GetCompaniesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/companies")
public class CompanyController implements CompanyApi {

    private final CompanyService companyService;

    @Override
    @GetMapping("")
    public GetCompaniesResponseDto getCompanies() {
        return companyService.getCompanies();
    }

    @Override
    @GetMapping("/{companyCode}/categories")
    public GetCompanyCategoriesByCodeResponseDto getCompanyCategoriesByCode(@PathVariable String companyCode) {
        return companyService.getCompanyCategoriesByCode(companyCode);
    }
}
