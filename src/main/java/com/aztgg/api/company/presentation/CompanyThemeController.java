package com.aztgg.api.company.presentation;

import com.aztgg.api.company.application.CompanyThemeService;
import com.aztgg.api.company.application.dto.GetCompanyThemesResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/company-themes")
public class CompanyThemeController implements CompanyThemeApi {

    private final CompanyThemeService companyThemeService;
    @Override
    @GetMapping("")
    public GetCompanyThemesResponseDto getCompanyThemes() {
        return companyThemeService.getCompanyThemes();
    }
}
