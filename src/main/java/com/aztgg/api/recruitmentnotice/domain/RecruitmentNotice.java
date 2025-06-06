package com.aztgg.api.recruitmentnotice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "recruitment_notice")
@NoArgsConstructor
public class RecruitmentNotice {

    @Id
    @Column(name = "recruitmentNoticeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitmentNoticeId;

    @Column(name = "companyCode")
    private String companyCode;

    @Column(name = "jobOfferTitle")
    private String jobOfferTitle;

    @Column(name = "hash")
    private String hash;

    @Column(name = "categories")
    private String categories;

    @Column(name = "standardCategory")
    private String standardCategory;

    @Column(name = "corporateCodes")
    private String corporateCodes;

    @Column(name = "url")
    private String url;

    @Column(name = "clickCount")
    private int clickCount;

    @Column(name = "scrapedAt")
    private LocalDateTime scrapedAt;

    @Column(name = "startAt")
    private LocalDateTime startAt;

    @Column(name = "endAt")
    private LocalDateTime endAt;

    @Builder
    public RecruitmentNotice(Long recruitmentNoticeId,
                             String companyCode,
                             String jobOfferTitle,
                             String hash,
                             String categories,
                             String standardCategory,
                             String corporateCodes,
                             String url,
                             int clickCount,
                             LocalDateTime scrapedAt,
                             LocalDateTime startAt,
                             LocalDateTime endAt) {
        this.recruitmentNoticeId = recruitmentNoticeId;
        this.companyCode = companyCode;
        this.jobOfferTitle = jobOfferTitle;
        this.hash = hash;
        this.categories = categories;
        this.standardCategory = standardCategory;
        this.corporateCodes = corporateCodes;
        this.url = url;
        this.clickCount = clickCount;
        this.scrapedAt = scrapedAt;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public void increaseClickCount() {
        this.clickCount++;
    }
}