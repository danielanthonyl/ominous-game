package com.darc.ominous.game.handlers;

import com.darc.ominous.game.model.domain.Character;
import com.darc.ominous.game.model.domain.inputs.FindCharacterInput;
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
                .onErrorResume(error -> ServerResponse.badRequest()
                        .bodyValue(new Response(error.getMessage(), error.getCause())));
    }

    public Mono<ServerResponse> findCharacter(ServerRequest request) {
        String characterId = request.queryParam("characterId").orElse(null);
        String characterName = request.queryParam("characterName").orElse(null);

        FindCharacterInput findCharacterInput = new FindCharacterInput();
        findCharacterInput.characterId = characterId;
        findCharacterInput.characterName = characterName;

        return Mono.just(findCharacterInput)
                .flatMap(FindCharacterInput::validateFields)
                .flatMap(characterService::findCharacter)
                .flatMap(character -> ServerResponse.ok().bodyValue(new Response(null, character)))
                .onErrorResume(error -> ServerResponse.badRequest()
                        .bodyValue(new Response(error.getMessage(), error.getCause())));
    }
}
