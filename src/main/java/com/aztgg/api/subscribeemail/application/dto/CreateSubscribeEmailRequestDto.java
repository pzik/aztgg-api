package com.aztgg.api.subscribeemail.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record CreateSubscribeEmailRequestDto(
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @NotEmpty(message = "standard-category 는 1개 이상이어야 합니다")
        Set<String> standardCategories) {
}
