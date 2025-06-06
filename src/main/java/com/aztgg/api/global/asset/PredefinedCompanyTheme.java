package com.aztgg.api.global.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public enum PredefinedCompanyTheme {

    NKLCB("네카라쿠배", Set.of(PredefinedCompany.NAVER.name(),
            PredefinedCompany.KAKAO.name(),
            PredefinedCompany.LINE.name(),
            PredefinedCompany.COUPANG.name(),
            PredefinedCompany.WOOWAHAN.name())),
    ;

    private final String korean;
    private final Set<String> companyCodes;

    public static PredefinedCompanyTheme fromCode(String themeCode) {
        for (var value : values()) {
            if (value.name().equals(themeCode)) {
                return value;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 themeCode");
    }
}
