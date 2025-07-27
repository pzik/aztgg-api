package com.aztgg.api.auth.application.dto.response;

import com.aztgg.api.auth.domain.User;

public record UserResponse(
    Long id,
    String username,
    String email,
    String nickname,
    String role
) {
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getNickname(),
            user.getRole().name()
        );
    }
}
