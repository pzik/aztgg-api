package com.aztgg.api.auth.application.strategy.impl;

import com.aztgg.api.auth.application.UserService;
import com.aztgg.api.auth.application.strategy.AuthCredentials;
import com.aztgg.api.auth.application.strategy.AuthStrategy;
import com.aztgg.api.auth.application.strategy.AuthType;
import com.aztgg.api.auth.domain.Role;
import com.aztgg.api.auth.domain.User;
import com.aztgg.api.auth.domain.UserDomainService;
import com.aztgg.api.auth.domain.exception.AuthErrorCode;
import com.aztgg.api.auth.domain.exception.AuthException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class KakaoAuthStrategy implements AuthStrategy {

    private final UserService userService;
    private final RestTemplate restTemplate;
    private final UserDomainService userDomainService;

    @Value("${kakao.api.url:https://kapi.kakao.com/v2/user/me}")
    private String kakaoApiUrl;

    public KakaoAuthStrategy(UserService userService, RestTemplate restTemplate, UserDomainService userDomainService) {
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.userDomainService = userDomainService;
    }

    @Override
    public AuthType getType() {
        return AuthType.KAKAO;
    }

    @Override
    public User authenticate(AuthCredentials credentials) {
        KakaoAuthCredentials kakaoCredentials = validateCredentials(credentials);
        Map<String, Object> userInfo = fetchKakaoUserInfo(kakaoCredentials.getAccessToken());

        Long kakaoId = extractKakaoId(userInfo);
        String email = extractEmail(userInfo, kakaoId);
        String username = "kakao_" + kakaoId;

        return userService.existsByUsername(username)
            ? userDomainService.findUserByUsername(username)
            : registerNewUser(username, email);
    }

    private KakaoAuthCredentials validateCredentials(AuthCredentials credentials) {
        if (!(credentials instanceof KakaoAuthCredentials kakaoCredentials)) {
            throw AuthException.invalidCredentials();
        }
        return kakaoCredentials;
    }

    private Map<String, Object> fetchKakaoUserInfo(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<Map> response = restTemplate.exchange(
                kakaoApiUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
            );

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS, "Kakao API response is invalid");
            }

            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS, "Kakao access token is invalid or expired.");
            }
            throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS, "Kakao API error: " + e.getMessage());
        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS, "Kakao auth failed: " + e.getMessage());
        }
    }

    private Long extractKakaoId(Map<String, Object> userInfo) {
        return Optional.ofNullable(userInfo.get("id"))
            .filter(id -> id instanceof Number)
            .map(id -> ((Number) id).longValue())
            .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_CREDENTIALS, "Kakao ID not found"));
    }

    private String extractEmail(Map<String, Object> userInfo, Long kakaoId) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
        return Optional.ofNullable(kakaoAccount)
            .map(acc -> (String) acc.get("email"))
            .orElse(kakaoId + "@kakao.user");
    }

    private User registerNewUser(String username, String email) {
        String randomPassword = UUID.randomUUID().toString();
        return userDomainService.createUser(username, randomPassword, email, Role.USER);
    }

    @Override
    public Class<? extends AuthCredentials> getCredentialsType() {
        return KakaoAuthCredentials.class;
    }
}
