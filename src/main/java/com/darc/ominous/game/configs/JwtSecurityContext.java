package com.darc.ominous.game.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.darc.ominous.game.utils.JWT;

import reactor.core.publisher.Mono;

@Component
public class JwtSecurityContext implements ServerSecurityContextRepository {
    @Autowired
    JwtAuthenticationManager jwtAuthenticationManager;
    @Autowired
    JWT jwt;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
        String token = getJwtFromHeader(serverWebExchange);

        if (token != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(token, token);
            return jwtAuthenticationManager.authenticate(authentication).map(SecurityContextImpl::new);
        } else {
            return Mono.empty();
        }
    }

    private String getJwtFromHeader(ServerWebExchange serverWebExchange) {
        String authorizationToken = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authorizationToken != null && authorizationToken.startsWith("Bearer ")) {
            return authorizationToken.substring(7);
        }

        return null;
    }
}