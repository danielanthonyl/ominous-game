package com.darc.ominous.game.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthenticationManager authenticationManager;

    @Autowired
    JwtSecurityContext securityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(authenticationManager);
        filter.setServerAuthenticationConverter(securityContextRepository);

        return serverHttpSecurity
                .authorizeExchange(config -> config
                        .pathMatchers(HttpMethod.POST, "/createUser").permitAll()
                        .pathMatchers(HttpMethod.POST, "/createUserSession").permitAll()
                        .anyExchange().authenticated())
                .addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(config -> config.disable())
                .formLogin(config -> config.disable())
                .csrf(config -> config.disable())
                .build();
    }
}
