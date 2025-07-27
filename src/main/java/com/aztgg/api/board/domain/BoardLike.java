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
@Table(name = "board_like")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BoardLike {

    @Id
    @Column(name = "boardLikeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardLikeId;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @Column(name = "userId")
    private Long userId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public BoardLike(Long boardLikeId, Board board, Long userId) {
        this.boardLikeId = boardLikeId;
        this.board = board;
        this.userId = userId;
    }
}
