package com.darc.ominous.game.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private String secret = "mysecretkeyyyyyyyyyyyyyyyyyyyyyy";
    private SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

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

    public String generate(String subject) {
        return Jwts
                .builder()
                .subject(subject)
                .signWith(key)
                .compact();
    }
}
