package com.aztgg.api.auth.presentation;

import com.aztgg.api.auth.application.dto.request.AdminLoginRequest;
import com.aztgg.api.auth.application.dto.request.KakaoLoginRequest;
import com.aztgg.api.auth.application.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 API")
@ApiResponses({
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content),
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content),
    @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR", content = @Content)
})
public interface AuthApi {

    @Operation(summary = "관리자 로그인", description = "관리자 계정으로 로그인합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    ResponseEntity<LoginResponse> adminLogin(AdminLoginRequest loginRequest);

    @Operation(summary = "카카오 로그인", description = "카카오 액세스 토큰으로 로그인합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    ResponseEntity<LoginResponse> kakaoLogin(KakaoLoginRequest loginRequest);

    @Operation(summary = "토큰 갱신", description = "리프레시 토큰으로 액세스 토큰을 갱신합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @PostMapping("/refresh")
    ResponseEntity<LoginResponse> refresh(String refreshToken);

    @Operation(summary = "로그아웃", description = "리프레시 토큰을 삭제하고 로그아웃 처리합니다.")
    @ApiResponse(responseCode = "204", description = "NO_CONTENT")
    @PostMapping("/logout")
    ResponseEntity<Void> logout(String refreshToken);
}
