package com.aztgg.api.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record KakaoLoginRequest (
    @NotBlank(message = "Access token is required")
    String kakaoToken
) {}
