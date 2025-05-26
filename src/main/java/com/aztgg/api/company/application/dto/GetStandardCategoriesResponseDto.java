package com.aztgg.api.company.application.dto;

import com.aztgg.api.company.domain.StandardCategory;

import java.util.List;

public record GetStandardCategoriesResponseDto(List<GetStandardCategoriesResponseItemDto> list) {

    public record GetStandardCategoriesResponseItemDto(String code, String name) {
        public static GetStandardCategoriesResponseItemDto from(StandardCategory standardCategory) {
            return new GetStandardCategoriesResponseItemDto(standardCategory.name(), standardCategory.getKorean());
        }
    }
}
