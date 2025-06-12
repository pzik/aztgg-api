package com.aztgg.api.hotissue.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentToHotIssueRequestDto(
        @NotBlank(message = "anonymousName, 필수 입력값입니다.")
        @Size(max = 100, message = "100자 까지 가능합니다.")
        String anonymousName,
        @NotBlank(message = "content, 필수 입력값입니다.")
        @Size(max = 500, message = "500자 까지 가능합니다.")
        String content) {
}
