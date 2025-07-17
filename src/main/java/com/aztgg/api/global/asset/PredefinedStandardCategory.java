package com.aztgg.api.global.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PredefinedStandardCategory {

    ETC("ETC", null, null),
    BACKEND("백엔드", "BE, Backend, 백엔드 개발, 백엔드 엔지니어, 데이터 엔지니어, 서버 개발, 데이터 엔지니어, Spring, Express, nestjs",
            "Data engineers also fall under 'BACKEND'."),
    FRONTEND("프론트엔드", "FE, 웹프론트엔드 개발자, React, next, vue, React Native",
            "Those who develop Android or iOS areas are among the app developers. (ANDROID or IOS)"),
    ANDROID("Android", "Android 개발자, Android Studio", "a person who creates an application for Android OS."),
    IOS("iOS", "iOS 개발자, Swift", "a person who creates an application for iOS."),
    DBA("DBA", "데이터베이스 관리자, MySQL, Postgres, Mongo", null),
    INFRA_DEVOPS("Infra/DevOps", "AWS, GCP, 인프라 엔지니어, 데브옵스 엔지니어", null),
    QA("QA", "QA, 품질보증, 테스트, Test Engineer", null),
    AI_ML("AI/머신러닝", "AI, 머신러닝, 딥러닝, LLM, LSM, Data Scientist, Pytorch",
            "Data Scientists who understand and solve complex data based on statistics, programming, machine learning, and domain knowledge also belong to 'AI_ML'."),
    DATA("데이터", "데이터 분석가, 빅데이터, SQL", "It has nothing to do with AI."),
    SERVICE_PLANNER("서비스 기획", "서비스 기획, 기획자", null),
    PM_PO("PM/PO", "PM, PO, Project Manager, Product Manager, Project Owner, Product Owner, 기술PM, TPM",
            "Technology PM is also part of the 'PM_PO'."),

    DESIGN("디자인", "디자이너, UI, UX, 웹디자인, 그래픽 디자이너",
            "Mainly refers to web/app designers. If it's not UI/UX design, consider other categories. (ART or GAME_DESIGN)"),
    ART("아트", "이펙터, Artist, 애니메이터, 이펙트 아티스트, Art, 뮤직, 시네마틱",
            "If it's game design, consider ART first"),
    GAME_DESIGN("게임 디자인", "모델러, 레벨러, 3D Modeling, Level Designer, System/Balance Designer, 전투 디자이너, NPC",
            "If it's UI/UX design, consider other categories. (DESIGN)"),

    GAME_PROGRAMMING("게임 개발", "유니티, 언리얼, 게임 서버, 게임 클라이언트 프로그래머, 게임 서버 프로그래머",
            "It refers to an engineer who develops a game client or server. " +
                    "Backend developers developing game server areas are also part of the 'GAME_PROGRAMMING'. " +
                    "However, game platform developers do not belong to 'GAME_PROGRAMMING'."),

    OPERATION("운영", "고객 지원, 운영 지원, 감사, 회계, 결산", null),
    SALES("영업", "Sales, Marketing", null),
    SECURITY("정보보안", "정보보안, 보안, 이상탐지", null),
    EMBEDDED("임베디드", "Robot, Robot Engineer, iOT, 임베디드 개발", null),
    HR("인사", "인사, 인사팀, 채용, 채용 관리자", null),
    MARKETING("마케팅", "마케터, 마케팅, Marketing Designer", null),
    ;

    private final String korean;
    private final String keyword;
    private final String tip;

    public static PredefinedStandardCategory fromCode(String code) {
        for (var value : values()) {
            if (value.name().equals(code)) {
                return value;
            }
        }
        return ETC;
    }
}

