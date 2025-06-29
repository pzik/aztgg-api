package com.aztgg.api.company.application;

import com.aztgg.api.company.application.dto.GetCompanyCategoriesByCodeResponseDto;
import com.aztgg.api.company.application.dto.GetCompaniesResponseDto;
import com.aztgg.api.company.application.dto.GetStandardCategoriesResponseDto;
import com.aztgg.api.global.asset.PredefinedCompany;
import com.aztgg.api.global.asset.PredefinedCompanyTheme;
import com.aztgg.api.global.asset.PredefinedStandardCategory;
import com.aztgg.api.global.exception.CommonErrorCode;
import com.aztgg.api.global.exception.CommonException;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final RecruitmentNoticeRepository recruitmentNoticeRepository;

    @Transactional(readOnly = true)
    public GetCompaniesResponseDto getCompanies(String themeCode) {
        List<GetCompaniesResponseDto.GetCompaniesItemDto> list = Arrays.stream(PredefinedCompany.values())
                .filter(PredefinedCompany::isNotUnknown)
                .filter(company -> {
                    // theme code 검색
                    if (Objects.nonNull(themeCode)) {
                        try {
                            PredefinedCompanyTheme companyTheme = PredefinedCompanyTheme.fromCode(themeCode);
                            return companyTheme.getCompanyCodes().contains(company.name());
                        } catch (IllegalArgumentException e) {
                            throw new CommonException(CommonErrorCode.BAD_REQUEST, "유효하지 않는 themeCode");
                        }
                    }
                    return true;
                })
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
        List<GetStandardCategoriesResponseDto.GetStandardCategoriesResponseItemDto> list = Arrays.stream(PredefinedStandardCategory.values())
                .map(GetStandardCategoriesResponseDto.GetStandardCategoriesResponseItemDto::from)
                .toList();

        return new GetStandardCategoriesResponseDto(list);
    }
}
