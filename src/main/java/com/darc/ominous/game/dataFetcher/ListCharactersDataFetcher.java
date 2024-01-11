package com.darc.ominous.game.dataFetcher;

import com.darc.ominous.game.model.domain.Character;
import com.darc.ominous.game.services.CharacterService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

@DgsComponent
public class ListCharactersDataFetcher {

    private final CharacterService characterService;

    @Autowired
    public ListCharactersDataFetcher(CharacterService characterService) {
        this.characterService = characterService;
    }


    @DgsQuery
    public Mono<Character[]> listCharacters() {
        return characterService.listCharacters();
    }
}
