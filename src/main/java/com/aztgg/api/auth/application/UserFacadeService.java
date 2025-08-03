package com.aztgg.api.auth.application;

import com.aztgg.api.auth.application.dto.request.AdminLoginRequest;
import com.aztgg.api.auth.application.dto.request.KakaoLoginRequest;
import com.aztgg.api.auth.application.dto.response.LoginResponse;
import com.aztgg.api.auth.application.dto.response.UserResponse;
import com.aztgg.api.auth.domain.User;
import com.aztgg.api.auth.domain.UserDomainService;

import com.aztgg.api.auth.application.strategy.impl.AdminAuthCredentials;
import com.aztgg.api.auth.application.strategy.impl.KakaoAuthCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFacadeService {

    private final AuthService authService;
    private final UserDomainService userDomainService;


    public LoginResponse adminLogin(AdminLoginRequest loginRequest) {
        AdminAuthCredentials credentials = AdminAuthCredentials.of(loginRequest.username(), loginRequest.password());
        return authService.authenticate(credentials);
    }

    public LoginResponse kakaoLogin(KakaoLoginRequest loginRequest) {
        KakaoAuthCredentials credentials = KakaoAuthCredentials.of(loginRequest.kakaoToken());
        return authService.authenticate(credentials);
    }

    public LoginResponse refresh(String refreshToken) {
        return authService.refresh(refreshToken);
    }

    public void logout(String refreshToken) {
        authService.logout(refreshToken);
    }

    public UserResponse getCurrentUser(String username) {
        User user = userDomainService.findUserByUsername(username);
        return UserResponse.from(user);
    }



    public void deleteUser(Long userId) {
        userDomainService.deleteUserById(userId);
    }

    @Transactional
    public UserResponse updateNickname(String username, String nickname) {
        User user = userDomainService.findUserByUsername(username);
        user = userDomainService.updateNickname(user, nickname);
        return UserResponse.from(user);
    }
}
