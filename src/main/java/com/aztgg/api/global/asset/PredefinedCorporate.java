package com.aztgg.api.global.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 법인(계열사)를 정의한다.
 * 모회사 <-> 계열사 매핑 관계를 가짐
 */
@Getter
@AllArgsConstructor
public enum PredefinedCorporate {

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

    // 카카오 그리팅 사용
    KAKAO_MOBILITY("KAKAO_MOBILITY", PredefinedCompany.KAKAO, "카카오모빌리티"),
    KAKAO_PAY("KAKAO_PAY", PredefinedCompany.KAKAO, "카카오페이"),
    KAKAO_ENTERPRISE("KAKAO_ENTERPRISE", PredefinedCompany.KAKAO, "카카오엔터프라이즈"),
    KAKAO_GAMES("KAKAO_GAMES", PredefinedCompany.KAKAO, "카카오게임즈"),

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

    // 네이버
    NAVER("NAVER", PredefinedCompany.NAVER, "네이버"),
    NAVER_CLOUD("NAVER Cloud", PredefinedCompany.NAVER, "네이버 클라우드"),
    SNOW("SNOW", PredefinedCompany.NAVER, "스노우"),
    NAVER_LABS("NAVER LABS", PredefinedCompany.NAVER, "네이버 랩스"),
    NAVER_WEBTOON("NAVER WEBTOON", PredefinedCompany.NAVER, "네이버 웹툰"),
    NAVER_FINANCIAL("NAVER FINANCIAL", PredefinedCompany.NAVER, "네이버 파이낸셜"),
    NAVER_IANDS("NAVER I&S", PredefinedCompany.NAVER, "네이버 아이앤애스"),

    // 넥슨
    NEXON_KOREA("넥슨코리아", PredefinedCompany.NEXON, "넥슨코리아"),
    NEXON_NEOPLE("네오플", PredefinedCompany.NEXON, "네오플"),
    NEXON_GAMES("넥슨게임즈", PredefinedCompany.NEXON, "넥슨게임즈"),
    NEXON_UNIVERSE("넥슨유니버스", PredefinedCompany.NEXON, "넥슨유니버스"),
    NEXON_DEVCAT("데브캣", PredefinedCompany.NEXON, "데브캣"),
    NEXON_NITRO("니트로", PredefinedCompany.NEXON, "니트로"),
    NEXON_ENGINE("엔진스튜디오", PredefinedCompany.NEXON, "엔진스튜디오"),
    NEXON_NETWORKS("넥슨네트웍스", PredefinedCompany.NEXON, "넥슨네트웍스"),
    NEXON_COMMUNICATIONS("넥슨커뮤니케이션즈", PredefinedCompany.NEXON, "넥슨커뮤니케이션즈"),
    NEXON_NMEDIA("엔미디어플랫폼", PredefinedCompany.NEXON, "엔미디어플랫폼"),
    NEXON_NXC("엔엑스씨", PredefinedCompany.NEXON, "엔엑스씨"),
    NEXON_MINT("민트로켓", PredefinedCompany.NEXON, "민트로켓"),
    NEXON_SPACE("넥슨스페이스", PredefinedCompany.NEXON, "넥슨스페이스"),

    // 크래프톤
    KRAFTON("KRAFTON", PredefinedCompany.KRAFTON, "크래프톤"),
    KRAFTON_PUBG("PUBGSTUDIOS", PredefinedCompany.KRAFTON, "PUBG Studio"),
    KRAFTON_BLUEHOLE("BlueholeStudio", PredefinedCompany.KRAFTON, "Bluehole Studio"),
    KRAFTON_STRIKING("StrikingDistanceStudios", PredefinedCompany.KRAFTON, "스트라이킹 디스턴스 스튜디오"),
    KRAFTON_RISING("RisingWings", PredefinedCompany.KRAFTON, "라이징윙스"),
    KRAFTON_5MINLAB("5minlab", PredefinedCompany.KRAFTON, "5민랩"),
    KRAFTON_0MNI("OmniCraftLabs", PredefinedCompany.KRAFTON, "옴니크래프트랩스"),
    KRAFTON_INZOI("inZOIStudio", PredefinedCompany.KRAFTON, "인조이 스튜디오"),
    KRAFTON_UNKNOWN_WORLDS("UnknownWorlds", PredefinedCompany.KRAFTON, "언노운 월드"),
    KRAFTON_MONTREAL("KRAFTONMontrealStudio", PredefinedCompany.KRAFTON, "KRAFTON MONTREAL"),
    KRAFTON_RELU("ReLUGames", PredefinedCompany.KRAFTON, "렐루게임즈"),
    KRAFTON_FLYWAY("FlywayGames", PredefinedCompany.KRAFTON, "Flyway Games"),
    KRAFTON_OVERDARE("OVERDARE", PredefinedCompany.KRAFTON, "OVERDARE"),
    KRAFTON_TANGO("TangoGameworks", PredefinedCompany.KRAFTON, "탱고게임웍스"),

    // 두나무
    DUNAMU("DUNAMU", PredefinedCompany.DUNAMU, "두나무"),

    // 몰로코
    MOLOCO("MOLOCO", PredefinedCompany.MOLOCO, "몰로코"),

    // 센드버드
    SENDBIRD("SENDBIRD", PredefinedCompany.SENDBIRD, "센드버드"),
    ;

    private final String id; // 각 스크랩 시 공고의 대상 법인을 식별하는 값
    private final PredefinedCompany parent; // 모회사
    private final String korean;

    public static PredefinedCorporate fromId(String id) {
        for (var value : values()) {
            if (value.id.equals(id)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public static PredefinedCorporate fromCode(String code) {
        for (var value : values()) {
            if (value.name().equals(code)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}

