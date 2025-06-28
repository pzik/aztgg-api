package com.aztgg.api.auth.domain;


public interface RefreshTokenManager {

	void save(String username, String refreshToken);

	String validateRefreshToken(String refreshToken);

	void rotateRefreshToken(String oldToken, String username, String newToken);

	void deleteRefreshToken(String refreshToken);
}
