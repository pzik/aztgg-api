package com.aztgg.api.company.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 법인(계열사)를 정의한다.
 * 모회사 <-> 계열사 매핑 관계를 가짐
 */
@Getter
@AllArgsConstructor
public enum Corporate {

    UNKNOWN("UNKNOWN", PredefinedCompany.UNKNOWN, "알수없음"),

    // 당근
    KARROT_MARKET("KARROT_MARKET", PredefinedCompany.DAANGN, "당근마켓"),
    KARROT_PAY("KARROT_PAY", PredefinedCompany.DAANGN, "당근페이"),

    // 토스
    TOSS("토스", PredefinedCompany.TOSS, "토스"),
    TOSS_PAYMENTS("토스페이먼츠", PredefinedCompany.TOSS, "토스페이먼츠"),
    TOSS_PLACE("토스플레이스", PredefinedCompany.TOSS, "토스플레이스"),
    TOSS_INSURANCE("토스인슈어런스", PredefinedCompany.TOSS, "토스인슈어런스"),
    TOSS_INVEST("토스증권", PredefinedCompany.TOSS, "토스증권"),
    TOSS_CX("토스씨엑스", PredefinedCompany.TOSS, "토스씨엑스"),
    TOSS_BANK("토스뱅크", PredefinedCompany.TOSS, "토스뱅크"),
    TOSS_INCOME("토스인컴", PredefinedCompany.TOSS, "토스인컴"),
    TOSS_INSIGHT("토스인사이트", PredefinedCompany.TOSS, "토스인사이트"),

    // 우아한형제들
    BAEMIN("BAEMIN", PredefinedCompany.WOOWAHAN, "배달의민족"),

    // 카카오
    KAKAO("KAKAO", PredefinedCompany.KAKAO, "카카오"),
    KAKAO_BANK("KAKAO_BANK", PredefinedCompany.KAKAO, "카카오뱅크"),

    // 라인
    LINE_PLUS("LINE Plus", PredefinedCompany.LINE, "LINE Plus"),
    LINE_FINANCIAL("LINE Financial Corp.", PredefinedCompany.LINE, "LINE Financial Corp."),
    LINE_STUDIO("LINE Studio", PredefinedCompany.LINE, "LINE Studio"),
    LINE_PAY_PLUS("LINE Pay Plus", PredefinedCompany.LINE, "LINE Pay Plus"),
    LINE_IPX("IPX", PredefinedCompany.LINE, "LINE IPX"),
    LINE_FRIENDS_SQUARE("LINE Friends Square", PredefinedCompany.LINE, "LINE Friends Square"),
    LINE_NEXT("LINE NEXT", PredefinedCompany.LINE, "LINE NEXT"),
    LINE_COMPANY_THAILAND("LINE Company Thailand", PredefinedCompany.LINE, "LINE Company Thailand"),
    LINE_PAY_THAILAND_LIMITED("LINE Pay (Thailand) Company Limited", PredefinedCompany.LINE, "LINE Pay (Thailand) Company Limited"),
    LINE_PAY_THAILAND_KOREA("LINE Pay (Thailand) Korea Office", PredefinedCompany.LINE, "LINE Pay (Thailand) Korea Office"),
    LINE_MAN_THAILAND("LINE MAN Thailand", PredefinedCompany.LINE, "LINE MAN Thailand"),
    LINE_TAIWAN("LINE Taiwan", PredefinedCompany.LINE, "LINE Taiwan"),
    LINE_PAY_TAIWAN("LINE Pay Taiwan", PredefinedCompany.LINE, "LINE Pay Taiwan"),
    LINE_BANK_TAIWAN("LINE Bank Taiwan", PredefinedCompany.LINE, "LINE Bank Taiwan"),
    LINE_FRIENDS_TAIWAN("LINE FRIENDS Taiwan", PredefinedCompany.LINE, "LINE FRIENDS Taiwan"),
    LINE_INDONESIA("LINE Indonesia", PredefinedCompany.LINE, "LINE Indonesia"),
    LINE_VIETNAM("LINE VIETNAM", PredefinedCompany.LINE, "LINE VIETNAM"),
    LINE_FRIENDS_AMERICA("LINE FRIENDS America", PredefinedCompany.LINE, "LINE FRIENDS America"),
    LINE_HONG_KONG("LINE Hong Kong", PredefinedCompany.LINE, "LINE Hong Kong"),
    LINE_INVESTMENT_TECH("LINE Investment Technologies", PredefinedCompany.LINE, "LINE Investment Technologies"),
    LINE_LV_CORP("LY Corporation", PredefinedCompany.LINE, "LINE LV Corporation"),


    ;

    private final String id; // 각 스크랩 시 공고의 대상 법인을 식별하는 값
    private final PredefinedCompany parent; // 모회사
    private final String korean;

    public static Corporate fromId(String id) {
        for (var value : values()) {
            if (value.id.equals(id)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public static Corporate fromCode(String code) {
        for (var value : values()) {
            if (value.name().equals(code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}

