package com.aztgg.api.company.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PredefinedCompany {

    NAVER("네이버", "네이버"),
    KAKAO("카카오", "카카오"),
    KAKAO_BANK("카카오뱅크", "카카오뱅크"),
    WOOWAHAN("배달의민족", "배달의민족"),
    TOSS("토스", "토스"),
    LINE("라인", "라인"),
    DAANGN("당근", "당근"),
    ;

    private final String korean;
    private final String description;
}
