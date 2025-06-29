package com.aztgg.api.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AdminLoginRequest(
    @NotBlank(message = "Username is required")
    String username,

    @NotBlank(message = "Password is required")
    String password
) {}
