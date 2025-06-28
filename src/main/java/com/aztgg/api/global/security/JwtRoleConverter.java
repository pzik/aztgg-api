package com.aztgg.api.global.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();

        Object rolesClaim = claims.get("roles");
        if (rolesClaim == null) {
            return Collections.emptyList();
        }

        if (rolesClaim instanceof Collection<?>) {
            return ((Collection<?>) rolesClaim).stream()
                .map(Object::toString)
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
        } else {
            String role = rolesClaim.toString();
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
        }
    }
}
