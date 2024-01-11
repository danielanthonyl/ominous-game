package com.darc.ominous.game.mutations;

import com.darc.ominous.game.model.domain.Character;
import com.darc.ominous.game.services.CharacterService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

@DgsComponent
public class CreateCharacterMutation {

    private final CharacterService characterService;

    @Autowired
    public CreateCharacterMutation(CharacterService characterService) {
        this.characterService = characterService;
    }

    @DgsMutation
    public Mono<Character> createCharacter(@InputArgument Character character) {
        Character.validateCharacterFields(character);
        Character.validateOccupation(character);

        return characterService.createCharacter(character);
    }
}
