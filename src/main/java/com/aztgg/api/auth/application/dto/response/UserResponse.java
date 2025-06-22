package com.aztgg.api.auth.application.dto.response;

import com.aztgg.api.auth.domain.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final String role;

    private UserResponse(Long id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole().name()
        );
    }
} 
