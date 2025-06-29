package com.aztgg.api.global.security;

import com.aztgg.api.auth.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final String keyId;

    @Value("${jwt.access-token}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, @Value("${jwt.key-id}") String keyId) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.keyId = keyId;
    }
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of(user.getRole().name()));
        return createToken(claims, user.getUsername(), accessTokenExpiration);
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenType", "refresh");
        return createToken(claims, user.getUsername(), refreshTokenExpiration);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationMs) {
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusMillis(expirationMs))
            .subject(subject)
            .claims(existingClaims -> existingClaims.putAll(claims))
            .build();

        JwsHeader header = JwsHeader.with(() -> "HS512")
            .keyId(keyId)
            .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claimsSet)).getTokenValue();
    }

    public String extractUsername(String token) {
        return getJwt(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        Jwt jwt = getJwt(token);
        Object rolesClaim = jwt.getClaim("roles");

        if (rolesClaim instanceof List<?>) {
            return ((List<?>) rolesClaim).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        } else if (rolesClaim != null) {
            return List.of(rolesClaim.toString());
        }

        return List.of();
    }

    public Boolean isTokenValid(String token) {
        try {
            Jwt jwt = getJwt(token);
            return !isTokenExpired(jwt);
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean isTokenExpired(Jwt jwt) {
        return jwt.getExpiresAt() != null && jwt.getExpiresAt().isBefore(Instant.now());
    }

    private Jwt getJwt(String token) {
        return jwtDecoder.decode(token);
    }
}
