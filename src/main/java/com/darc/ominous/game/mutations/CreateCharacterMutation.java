package com.darc.ominous.game.mutations;

import com.darc.ominous.game.model.domain.Character;
import com.darc.ominous.game.model.domain.inputs.CreateCharacterInput;
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
    public Mono<Character> createCharacter(@InputArgument CreateCharacterInput characterInput) {
        // DEBT! fix the chain so we don't need this here
        Character character = new Character();
        character.name = characterInput.name;
        character.occupation = characterInput.occupation;
        //

        Character.validateCharacterFields(character);
        Character.validateOccupation(character);

        return characterService.createCharacter(character);
    }
}
