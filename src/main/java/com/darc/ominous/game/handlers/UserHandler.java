package com.darc.ominous.game.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.darc.ominous.game.model.domain.User;
import com.darc.ominous.game.services.UserService;
import com.darc.ominous.game.utils.Response;

import reactor.core.publisher.Mono;

@Component
public class UserHandler {
    @Autowired
    private UserService userService;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> requestMonoUser = request.bodyToMono(User.class);

        return requestMonoUser
                .flatMap(userService::createUser)
                .flatMap(token -> ServerResponse.ok().bodyValue(new Response("user created successfully", token)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(new Response(e.getMessage(), e.getCause())));
    }

    public Mono<ServerResponse> createUserSession(ServerRequest request) {
        Mono<User> requestMonoUser = request.bodyToMono(User.class);

        return requestMonoUser
                .flatMap(userService::createUserSession)
                .flatMap(token -> ServerResponse.ok()
                        .bodyValue(new Response("user session successfully created", token)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(new Response(e.getMessage(), e.getCause())));
    }
}
