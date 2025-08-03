package com.aztgg.api.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NicknameUpdateRequest(
    @NotBlank(message = "Nickname is required")
    @Size(max = 20, message = "Nickname cannot be longer than 20 characters")
    String nickname
) {}
