package com.darc.ominous.game.routers;

import com.darc.ominous.game.handlers.CharacterHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CharacterRouter {

    @Bean
    RouterFunction<ServerResponse> createCharacterRoute(CharacterHandler characterHandler) {
        return RouterFunctions.route(RequestPredicates.POST("/createCharacter"), characterHandler::createCharacter);
    }
}
