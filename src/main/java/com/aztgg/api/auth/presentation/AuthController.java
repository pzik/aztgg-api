package com.aztgg.api.auth.presentation;

import com.aztgg.api.auth.application.AuthService;
import com.aztgg.api.auth.application.dto.request.AdminLoginRequest;
import com.aztgg.api.auth.application.dto.request.KakaoLoginRequest;
import com.aztgg.api.auth.application.dto.response.LoginResponse;
import com.aztgg.api.auth.application.strategy.impl.AdminAuthCredentials;
import com.aztgg.api.auth.application.strategy.impl.KakaoAuthCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@RequestBody AdminLoginRequest loginRequest) {
        AdminAuthCredentials credentials = AdminAuthCredentials.of(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(authService.authenticate(credentials));
    }

    @Override
    @PostMapping("/kakao/login")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestBody KakaoLoginRequest loginRequest) {
        KakaoAuthCredentials credentials = KakaoAuthCredentials.of(loginRequest.getKakaoToken());
        return ResponseEntity.ok(authService.authenticate(credentials));
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@CookieValue("refreshToken") String refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue("refreshToken") String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
