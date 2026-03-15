package com.aztgg.api.subscribeemail.presentation;

import com.aztgg.api.subscribeemail.application.dto.CreateSubscribeEmailRequestDto;
import com.aztgg.api.subscribeemail.application.dto.GetSubscribeEmailCountResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "SubscribeEmail", description = "이메일 구독 API")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "BAD_REQUEST",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "500",
                description = "INTERNAL_SERVER_ERROR",
                content = @Content
        ),
})
public interface SubscribeEmailApi {

    @Operation(tags = {"SubscribeEmail"}, summary = "이메일 구독 생성", description = """
            ## API 설명
            이메일 구독을 생성합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    void createSubscribeEmail(CreateSubscribeEmailRequestDto payload);

    @Operation(tags = {"SubscribeEmail"}, summary = "이메일 구독 해지", description = """
            ## API 설명
            이메일 구독을 해지합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    String unsubscribeEmail(String email);

    @Operation(tags = {"SubscribeEmail"}, summary = "구독자 수 조회", description = """
            ## API 설명
            현재 이메일 구독자 수를 조회합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    GetSubscribeEmailCountResponseDto getSubscribeEmailCount();
}
