package com.aztgg.api.hotissue.domain;

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
@Table(name = "hot_issue_comment")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class HotIssueComment {

    @Id
    @Column(name = "hotIssueCommentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotIssueCommentId;

    @ManyToOne
    @JoinColumn(name = "hotIssue")
    private HotIssue hotIssue;

    @Column(name = "ip")
    private String ip;

    @Column(name = "anonymousName")
    private String anonymousName;

    @Column(name = "content")
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public HotIssueComment(Long hotIssueCommentId, String ip, String anonymousName, String content) {
        this.hotIssueCommentId = hotIssueCommentId;
        this.ip = ip;
        this.anonymousName = anonymousName;
        this.content = content;
    }

    public void updateParent(HotIssue hotIssue) {
        this.hotIssue = hotIssue;
    }
}
