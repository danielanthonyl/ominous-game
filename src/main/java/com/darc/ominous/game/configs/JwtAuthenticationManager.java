package com.darc.ominous.game.configs;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.darc.ominous.game.utils.JWT;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private JWT jwt;

    public JwtAuthenticationManager() {
        this.jwt = new JWT();
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = jwt.getUsernameFromToken(authToken);

        Authentication result = new UsernamePasswordAuthenticationToken(username, authToken, authentication.getAuthorities());

        return Mono.just(result);
    }
}
