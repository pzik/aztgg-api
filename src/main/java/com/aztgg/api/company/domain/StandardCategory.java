package com.aztgg.api.company.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StandardCategory {

    ETC("ETC", null),
    BACKEND("백엔드", "BE, Backend, 백엔드 개발, 백엔드 엔지니어, 데이터 엔지니어, 서버 개발, 데이터 엔지니어, Spring, Express, nestjs"),
    FRONTEND("프론트엔드","FE, 웹프론트엔드 개발자, React, next, vue, React Native"),
    ANDROID("Android", "Android 개발자, Android Studio"),
    IOS("iOS","iOS 개발자, Swift"),
    EMBEDDED("임베디드", "Robot, Robot Engineer, iOT, 임베디드 개발"),
    DBA("DBA", "데이터베이스 관리자, MySQL, Postgres, Mongo"),
    HR("인사", "인사, 인사팀, 채용, 채용 관리자"),
    INFRA_DEVOPS("Infra/DevOps", "AWS, GCP, 인프라 엔지니어, 데브옵스 엔지니어"),
    DESIGN("디자인", "디자이너, UI, UX, 그래픽 디자인, Product Designer"),
    QA("QA", "QA, 품질보증, 테스트, Test Engineer"),
    AI_ML("AI/머신러닝", "AI, 머신러닝, 딥러닝, LLM, LSM, Data Scientist, Pytorch"),
    DATA("데이터", "데이터 분석가, 빅데이터"),
    SECURITY("정보보안", "정보보안, 보안, 이상탐지"),
    SERVICE_PLANNER("서비스 기획", "서비스 기획, PM, PO, Project Manager, Product Manager, Project Owner, Product Owner"),
    OPERATION("운영", "고객 지원, 운영 지원, 감사"),
    SALES("영업", "Sales"),
    ;

    private final String korean;
    private final String keyword;
}