package com.darc.ominous.game.configs;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.darc.ominous.game.utils.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private JwtUtil jwtUtil;

    public JwtAuthenticationManager() {
        this.jwtUtil = new JwtUtil();
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        try {

            String authToken = authentication.getPrincipal().toString();

            Boolean isValid = jwtUtil.validate(authToken);

            if (isValid) {
                return Mono.justOrEmpty(authentication);
            }

            return Mono.error(new BadCredentialsException("Invalid bearer token."));
        } catch (Exception e) {
            return Mono.error(new BadCredentialsException(e.getMessage(), e.getCause()));
        }
    }
}
