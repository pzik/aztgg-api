package com.aztgg.api.company.application.dto;

import com.aztgg.api.global.asset.PredefinedStandardCategory;

import java.util.List;

public record GetStandardCategoriesResponseDto(List<GetStandardCategoriesResponseItemDto> list) {

    public record GetStandardCategoriesResponseItemDto(String code, String name) {
        public static GetStandardCategoriesResponseItemDto from(PredefinedStandardCategory predefinedStandardCategory) {
            return new GetStandardCategoriesResponseItemDto(predefinedStandardCategory.name(), predefinedStandardCategory.getKorean());
        }
    }
}
