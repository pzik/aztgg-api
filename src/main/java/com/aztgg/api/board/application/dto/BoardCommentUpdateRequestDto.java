package com.aztgg.api.board.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BoardCommentUpdateRequestDto(@NotBlank(message = "content, 필수 입력값입니다.")
                                           @Size(max = 300, message = "300자 까지 가능합니다.")
                                           String content) {
}
