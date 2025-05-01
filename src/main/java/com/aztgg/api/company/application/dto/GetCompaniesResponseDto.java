package com.aztgg.api.company.application.dto;

import java.util.List;

public record GetCompaniesResponseDto(List<GetCompaniesItemDto> companies) {

    public record GetCompaniesItemDto(String companyCode, String name) {

    }
}
