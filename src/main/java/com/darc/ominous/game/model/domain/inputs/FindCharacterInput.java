package com.darc.ominous.game.model.domain.inputs;

import com.darc.ominous.game.utils.Utils;
import reactor.core.publisher.Mono;

public class FindCharacterInput {
    public String characterId;
    public String characterName;

    public static Mono<FindCharacterInput> validateFields(FindCharacterInput findCharacterInput) {
        if (Utils.isEmpty(findCharacterInput.characterId) && Utils.isEmpty(findCharacterInput.characterName)) {
            throw new RuntimeException("Either pass a characterId or characterName parameter.");
        }

        return Mono.just(findCharacterInput);
    }
}
