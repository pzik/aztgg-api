package com.aztgg.api.global.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collection;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtRoleConverter jwtRoleConverter;

    public CustomJwtAuthenticationConverter(JwtRoleConverter jwtRoleConverter) {
        this.jwtRoleConverter = jwtRoleConverter;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = jwtRoleConverter.convert(jwt);

        CustomUserPrincipal principal = new CustomUserPrincipal(
            jwt.getSubject(),
            jwt.getClaimAsString("email")
        );

        CustomJwtAuthenticationToken token = new CustomJwtAuthenticationToken(jwt, authorities, principal);

        return token;
    }
}
