package com.aztgg.api.recruitmentnoticestatistic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "recruitment_notice_statistic")
public class RecruitmentNoticeStatistic {

    @Id
    @Column(name = "recruitmentNoticeStatisticId")
    private Long recruitmentNoticeStatisticId;

    @Column(name = "standardCategory")
    private String standardCategory;

    @Column(name = "companyCode")
    private String companyCode;

    @Column(name = "count")
    private Integer count;

    @Column(name = "createdAt")
    private LocalDate createdAt;
}