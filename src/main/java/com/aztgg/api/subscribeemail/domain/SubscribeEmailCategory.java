package com.aztgg.api.subscribeemail.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "subscribe_email_category")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SubscribeEmailCategory {

    @Id
    @Column(name = "subscribeEmailCategoryId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscribeEmailCategoryId;

    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "subscribeEmailId")
    private SubscribeEmail subscribeEmail;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public SubscribeEmailCategory(Long subscribeEmailCategoryId, String category) {
        this.subscribeEmailCategoryId = subscribeEmailCategoryId;
        this.category = category;
    }

    public void updateParent(SubscribeEmail subscribeEmail) {
        this.subscribeEmail = subscribeEmail;
    }
}
