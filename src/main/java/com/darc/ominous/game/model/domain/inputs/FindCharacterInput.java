package com.darc.ominous.game.model.domain.inputs;

import com.darc.ominous.game.utils.Utils;
import reactor.core.publisher.Mono;

public class FindCharacterInput {
    public String characterId;
    public String characterName;

    public static Mono<String> validateFields(String identifier) {
        if (Utils.isEmpty(identifier)) {
            throw new RuntimeException("Either pass a characterId or characterName parameter.");
        }

        return Mono.just(identifier);
    }
}
