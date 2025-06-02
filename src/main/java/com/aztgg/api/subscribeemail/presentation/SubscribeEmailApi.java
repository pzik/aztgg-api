package com.aztgg.api.subscribeemail.presentation;

import com.aztgg.api.subscribeemail.application.dto.CreateSubscribeEmailRequestDto;
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
}
