package com.aztgg.api.global.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

public class CustomJwtAuthenticationToken extends JwtAuthenticationToken {
    
    private final CustomUserPrincipal principal;
    
    public CustomJwtAuthenticationToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities, CustomUserPrincipal principal) {
        super(jwt, authorities, principal.getUserId());
        this.principal = principal;
    }
    
    @Override
    public Object getPrincipal() {
        return principal;
    }
}
