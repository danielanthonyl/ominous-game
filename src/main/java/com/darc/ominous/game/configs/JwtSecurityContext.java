package com.darc.ominous.game.configs;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtSecurityContext implements ServerSecurityContextRepository {

    /**
     * Saves the SecurityContext
     *
     * @param exchange the exchange to associate to the SecurityContext
     * @param context  the SecurityContext to save
     * @return a completion notification (success or error)
     */
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return null;
    }

    /**
     * Loads the SecurityContext associated with the {@link ServerWebExchange}
     *
     * @param exchange the exchange to look up the {@link SecurityContext}
     * @return the {@link SecurityContext} to look up or empty if not found. Never null
     */
    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            return Mono.empty();
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(token, null, null);
        SecurityContext securityContext = new SecurityContextImpl(authentication);

        return Mono.just(securityContext);
    }
}