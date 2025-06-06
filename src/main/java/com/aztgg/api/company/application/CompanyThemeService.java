package com.aztgg.api.company.application;

import com.aztgg.api.company.application.dto.GetCompanyThemeResponseDto;
import com.aztgg.api.company.application.dto.GetCompanyThemesResponseDto;
import com.aztgg.api.global.asset.PredefinedCompanyTheme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyThemeService {

    public GetCompanyThemesResponseDto getCompanyThemes() {
        List<GetCompanyThemeResponseDto> companyThemeDtoList = Arrays.stream(PredefinedCompanyTheme.values())
                .map(a -> new GetCompanyThemeResponseDto(a.name(), a.getKorean()))
                .toList();

        return new GetCompanyThemesResponseDto(companyThemeDtoList);
    }
}
