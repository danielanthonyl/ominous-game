package com.darc.ominous.game.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.darc.ominous.game.model.domain.User;
import com.darc.ominous.game.services.UserService;
import com.darc.ominous.game.utils.JWT;
import com.darc.ominous.game.utils.Response;

import reactor.core.publisher.Mono;

@Component
public class UserHandler {
    @Autowired
    private UserService userService;
    private JWT jwt;

    public UserHandler() {
        this.jwt = new JWT();
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> requestMonoUser = request.bodyToMono(User.class);
        Mono<Response> createUserResponse = requestMonoUser.flatMap(userService::createUser);

        return createUserResponse
                .flatMap(userResponse -> ServerResponse.ok().bodyValue(userResponse))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        Mono<User> requestMonoUser = request.bodyToMono(User.class);

        return requestMonoUser.flatMap(user -> {
            String jwtToken = jwt.generateJwt(user.username());
            Response response = new Response("Logged in successfully", jwtToken);

            return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(response);
        }).switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED).build());
    }
}
