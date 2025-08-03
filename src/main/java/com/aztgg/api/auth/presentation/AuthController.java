package com.aztgg.api.auth.presentation;

import com.aztgg.api.auth.application.UserFacadeService;
import com.aztgg.api.auth.application.dto.request.AdminLoginRequest;
import com.aztgg.api.auth.application.dto.request.KakaoLoginRequest;
import com.aztgg.api.auth.application.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController implements AuthApi {

    private final UserFacadeService userFacadeService;

    @Override
    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@RequestBody AdminLoginRequest loginRequest) {
        return ResponseEntity.ok(userFacadeService.adminLogin(loginRequest));
    }

    @Override
    @PostMapping("/kakao/login")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestBody KakaoLoginRequest loginRequest) {
        return ResponseEntity.ok(userFacadeService.kakaoLogin(loginRequest));
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@CookieValue("refreshToken") String refreshToken) {
        return ResponseEntity.ok(userFacadeService.refresh(refreshToken));
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue("refreshToken") String refreshToken) {
        userFacadeService.logout(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
