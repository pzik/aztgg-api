package com.aztgg.api.board.application.dto;

import java.util.List;

public record BoardWithNicknameListResponseDto(List<BoardWithNicknameResponseDto> list, MetadataDto metadata) {

    public record MetadataDto(long totalElements) {

    }

    public static BoardWithNicknameListResponseDto of(List<BoardWithNicknameResponseDto> boards, long totalElements) {
        MetadataDto metadata = new MetadataDto(totalElements);
        return new BoardWithNicknameListResponseDto(boards, metadata);
    }
}
