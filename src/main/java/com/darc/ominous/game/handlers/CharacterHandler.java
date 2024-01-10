package com.darc.ominous.game.handlers;

import com.darc.ominous.game.model.domain.Character;
import com.darc.ominous.game.services.CharacterService;
import com.darc.ominous.game.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CharacterHandler {
    @Autowired
    CharacterService characterService;

    public Mono<ServerResponse> createCharacter(ServerRequest request) {

        return request
                .bodyToMono(Character.class)
                .flatMap(Character::validateCharacterFields)
                .flatMap(Character::validateOccupation)
                .flatMap(characterService::createCharacter)
                .then(ServerResponse.ok().bodyValue(new Response("Character created successfully.", null)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(new Response(e.getMessage(), e.getCause())));
    }
}
