package com.darc.ominous.game.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final String secret = "mysecretkeyyyyyyyyyyyyyyyyyyyyyy";
    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

    public Claims getClaims(String token) throws MalformedJwtException {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean validate(String token) throws MalformedJwtException {
        return getClaims(token).getSubject() != null;
    }

    public Mono<String> getSubject() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    try {
                        String token = authentication.getPrincipal().toString();
                        return this.getClaims(token).getSubject();
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to extract subject from token", e);
                    }
                });

    }

    public String generate(String subject) {
        return Jwts
                .builder()
                .subject(subject)
                .signWith(key)
                .compact();
    }

}
