package com.darc.ominous.game.dataFetcher;

import com.darc.ominous.game.model.domain.Character;
import com.darc.ominous.game.model.domain.inputs.FindCharacterInput;
import com.darc.ominous.game.services.CharacterService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

@DgsComponent
public class FindCharacterDataFetcher {
    CharacterService characterService;

    @Autowired
    public FindCharacterDataFetcher(CharacterService characterService) {
        this.characterService = characterService;
    }

    @DgsQuery
    public Mono<Character> findCharacter(@InputArgument FindCharacterInput findCharacterInput) {
        FindCharacterInput.validateFields(findCharacterInput);

        return characterService.findCharacter(findCharacterInput);
    }
}
