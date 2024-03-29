package com.darc.ominous.game.routers;

import com.darc.ominous.game.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

    @Bean
    RouterFunction<ServerResponse> createUserRoute(UserHandler userHandler) {
        return RouterFunctions.route(RequestPredicates.POST("/createUser"), userHandler::createUser);
    }

    @Bean
    RouterFunction<ServerResponse> createUserSessionRoute(UserHandler userHandler) {
        return RouterFunctions.route(RequestPredicates.POST("/createUserSession"), userHandler::createUserSession);
    }


}
