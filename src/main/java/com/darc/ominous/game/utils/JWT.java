package com.darc.ominous.game.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class JWT {
    private String secret = "mysecretkeyyyyyyyyyyyyyyyyyyyyyy";
    private SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

    public String getUsernameFromToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String generateJwt(String username) {
        return Jwts.builder().subject(username).signWith(key).compact();
    }
}
