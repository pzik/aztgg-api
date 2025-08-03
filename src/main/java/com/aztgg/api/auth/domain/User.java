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

import com.aztgg.api.auth.domain.exception.InvalidEmailDomainException;
import com.aztgg.api.auth.domain.exception.InvalidNicknameDomainException;
import com.aztgg.api.auth.domain.exception.InvalidPasswordDomainException;
import com.aztgg.api.auth.domain.exception.InvalidRoleDomainException;
import com.aztgg.api.auth.domain.exception.InvalidUsernameDomainException;
import com.aztgg.api.auth.domain.exception.NicknameEmptyDomainException;
import com.aztgg.api.auth.domain.exception.NicknameTooLongDomainException;
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
            throw new InvalidNicknameDomainException();
        }
        if (username == null || username.isBlank()) {
            throw new InvalidUsernameDomainException();
        }
        if (password == null || password.length() < 20) {
            throw new InvalidPasswordDomainException();
        }
        if (!EmailValidator.isValid(email)) {
            throw new InvalidEmailDomainException();
        }
        if (role == null) {
            throw new InvalidRoleDomainException();
        }
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }
    public boolean isUser() {
        return this.role == Role.USER;
    }

    public void changeNickname(String newNickname) {
        if (newNickname == null || newNickname.trim().isEmpty()) {
            throw new NicknameEmptyDomainException();
        }
        if (newNickname.length() > 20) {
            throw new NicknameTooLongDomainException();
        }
        this.nickname = newNickname;
    }
}
