package com.darc.ominous.game.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthenticationManager authenticationManager;

    @Autowired
    JwtSecurityContext securityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        System.out.println("reached the security chain");

        return serverHttpSecurity
                .csrf(config -> config.disable())
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(config -> config
                        .pathMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyExchange().authenticated())
                .build();
    }
}
