package com.aztgg.api.auth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

import com.aztgg.api.auth.domain.exception.AuthException;
import com.aztgg.api.global.validator.EmailValidator;
import com.aztgg.api.global.validator.NicknameValidator;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public User(String username, String password, String email, String nickname, Role role) {
        if (!NicknameValidator.isValid(nickname)) {
            throw AuthException.invalidNickname();
        }
        if (username == null || username.isBlank()) {
            throw AuthException.invalidUsername();
        }
        if (password == null || password.length() < 20) {
            throw AuthException.invalidPassword();
        }
        if (!EmailValidator.isValid(email)) {
            throw AuthException.invalidEmail();
        }
        if (role == null) {
            throw AuthException.invalidRole();
        }
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public boolean isUser() {
        return this.role == Role.USER;
    }


    public boolean matchesUsername(String username) {
        return this.username.equals(username);
    }

    public void changeNickname(String newNickname) {
        if (newNickname == null || newNickname.trim().isEmpty()) {
            throw AuthException.nicknameEmpty();
        }
        if (newNickname.length() > 20) {
            throw AuthException.nicknameTooLong();
        }
        this.nickname = newNickname;
    }
}
