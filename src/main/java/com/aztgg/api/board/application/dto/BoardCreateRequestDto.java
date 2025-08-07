package com.aztgg.api.board.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BoardCreateRequestDto(@NotBlank(message = "title, 필수 입력값입니다.")
                                    @Size(max = 70, message = "70자 까지 가능합니다.")
                                    String title,
                                    @NotBlank(message = "content, 필수 입력값입니다.")
                                    @Size(max = 3000, message = "3000자 까지 가능합니다.")
                                    String content) {
}
