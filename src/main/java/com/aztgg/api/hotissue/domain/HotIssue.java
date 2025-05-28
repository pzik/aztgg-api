package com.aztgg.api.hotissue.domain;

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
@Table(name = "hot_issue")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class HotIssue {

    @Id
    @Column(name = "hotIssueId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotIssueId;

    @Column(name = "recruitmentNoticeId")
    private Long recruitmentNoticeId;

    @OneToMany(mappedBy = "hotIssue", cascade = {CascadeType.ALL})
    private List<HotIssueComment> comments = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public HotIssue(Long hotIssueId, Long recruitmentNoticeId, List<HotIssueComment> comments) {
        this.hotIssueId = hotIssueId;
        this.recruitmentNoticeId = recruitmentNoticeId;
        this.comments = comments;

        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
    }

    public void addComment(HotIssueComment comment) {
        this.comments.add(comment);
        comment.updateParent(this);
    }
}
