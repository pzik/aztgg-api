package com.aztgg.api.board.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Table(name = "board")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @Column(name = "boardId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(name = "userId")
    private Long userId;

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "board", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<BoardComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<BoardLike> likes = new ArrayList<>();

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public Board(Long boardId, Long userId, String title, String content, List<BoardComment> comments, List<BoardLike> likes) {
        this.boardId = boardId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.likes = likes;

        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        if (this.likes == null) {
            this.likes = new ArrayList<>();
        }
    }

    public String getDefaultUsername() {
        return "UNKNOWN";
    }

    public Set<Long> findAllRelatedUserId() {
        Set<Long> set = new HashSet<>();
        set.add(userId);
        for (var i : comments) {
            set.add(i.getUserId());
        }
        for (var i : likes) {
            set.add(i.getUserId());
        }
        return set;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void addLike(Long userId) {
        BoardLike boardLike = BoardLike.builder()
                .userId(userId)
                .board(this)
                .build();

        this.likes.add(boardLike);
    }

    public void removeLike(Long userId) {
        Optional<BoardLike> origin = this.likes.stream()
                .filter(a -> a.getUserId().equals(userId))
                .findAny();

        origin.ifPresent(boardLike -> this.likes.remove(boardLike));
    }

    public Optional<BoardLike> getMyLike(Long userId) {
        return this.likes.stream()
                .filter(a -> a.getUserId().equals(userId))
                .findAny();
    }

    public void addComment(Long userId, String content) {
        BoardComment boardComment = BoardComment.builder()
                .userId(userId)
                .content(content)
                .board(this)
                .build();

        this.comments.add(boardComment);
    }

    public void deleteCommentById(Long boardCommentId) {
        for (var boardComment :comments) {
            if (boardComment.getBoardCommentId().equals(boardCommentId)) {
                comments.remove(boardComment);
                return;
            }
        }
        throw new IllegalStateException("not found comment");
    }

    public BoardComment getCommentByCommentId(Long boardCommentId) {
        for (var boardComment :comments) {
            if (boardComment.getBoardCommentId().equals(boardCommentId)) {
                return boardComment;
            }
        }
        throw new IllegalStateException("not found comment");
    }
}
