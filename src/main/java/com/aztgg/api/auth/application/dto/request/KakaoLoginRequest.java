package com.aztgg.api.auth.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class KakaoLoginRequest {
    @NotBlank(message = "Access token is required")
    private String kakaoToken;
}
