package com.aztgg.api.board.domain;

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
@Table(name = "board_comment")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BoardComment {

    @Id
    @Column(name = "boardCommentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardCommentId;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "content")
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public BoardComment(Long boardCommentId, Board board, Long userId, String content) {
        this.boardCommentId = boardCommentId;
        this.board = board;
        this.userId = userId;
        this.content = content;
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
