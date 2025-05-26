package com.aztgg.api.company.application;

import com.aztgg.api.company.application.dto.GetCompanyCategoriesByCodeResponseDto;
import com.aztgg.api.company.application.dto.GetCompaniesResponseDto;
import com.aztgg.api.company.application.dto.GetStandardCategoriesResponseDto;
import com.aztgg.api.company.domain.PredefinedCompany;
import com.aztgg.api.company.domain.StandardCategory;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final RecruitmentNoticeRepository recruitmentNoticeRepository;

    public GetCompaniesResponseDto getCompanies() {
        List<GetCompaniesResponseDto.GetCompaniesItemDto> list = Arrays.stream(PredefinedCompany.values())
                .filter(PredefinedCompany::isNotUnknown)
                .map(company -> new GetCompaniesResponseDto.GetCompaniesItemDto(company.name(), company.getKorean()))
                .collect(Collectors.toList());

        return new GetCompaniesResponseDto(list);
    }

    @Transactional(readOnly = true)
    public GetCompanyCategoriesByCodeResponseDto getCompanyCategoriesByCode(String companyCode) {
        Set<String> categories = recruitmentNoticeRepository.findAllByCompanyCode(companyCode).stream()
                .map(RecruitmentNotice::getCategories)
                .flatMap(a -> StringUtils.commaDelimitedListToSet(a).stream())
                .collect(Collectors.toSet());
        return new GetCompanyCategoriesByCodeResponseDto(categories);
    }

    public GetStandardCategoriesResponseDto getStandardCategories() {
        List<GetStandardCategoriesResponseDto.GetStandardCategoriesResponseItemDto> list = Arrays.stream(StandardCategory.values())
                .map(GetStandardCategoriesResponseDto.GetStandardCategoriesResponseItemDto::from)
                .toList();

        return new GetStandardCategoriesResponseDto(list);
    }
}
