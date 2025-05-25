package com.aztgg.api.subscribeemail.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "subscribe_email")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SubscribeEmail {

    @Id
    @Column(name = "subscribeEmailId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscribeEmailId;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "subscribeEmail", cascade = {CascadeType.ALL})
    private List<SubscribeEmailCategory> categories = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public SubscribeEmail(Long subscribeEmailId, String email, List<SubscribeEmailCategory> categories) {
        this.subscribeEmailId = subscribeEmailId;
        this.email = email;
        this.categories = categories;

        if (this.categories == null) {
            this.categories = new ArrayList<>();
        }
    }

    public void addCategory(SubscribeEmailCategory subscribeEmailCategory) {
        this.categories.add(subscribeEmailCategory);
        subscribeEmailCategory.updateParent(this);
    }
}
