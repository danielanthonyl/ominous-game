package com.darc.ominous.game.services;

import com.darc.ominous.game.mappers.CharacterMapper;
import com.darc.ominous.game.model.domain.Character;
import com.darc.ominous.game.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CharacterService {
    @Autowired
    CharacterMapper characterMapper;

    @Autowired
    JwtUtil jwtUtil;

    public Mono<Character> createCharacter(Character character) {
        return jwtUtil
                .getSubject()
                .flatMap(userId -> {
                    Character repeatedCharacter = characterMapper.findCharacter(null, null, character.name);

                    if (!Objects.isNull(repeatedCharacter)) {
                        throw new RuntimeException("Character name already taken.");
                    }

                    character.setUserId(userId);
                    characterMapper.createCharacter(character);
                    Character newCharacter = characterMapper.findCharacter(userId, character.id, character.name);
                    System.out.println(newCharacter);

                    if (Objects.isNull(newCharacter)) {
                        throw new RuntimeException("Couldn't create character.");
                    }

                    return Mono.just(newCharacter);
                });
    }

    public Mono<Character> findCharacter(String identifier) {
        return jwtUtil.getSubject()
                .flatMap(userId -> {
                    Character foundCharacter = characterMapper.findCharacter(userId, identifier, identifier);

                    if (foundCharacter == null) {
                        return Mono.error(new RuntimeException("Couldn't find a character with name or id: " + identifier));
                    }

                    return Mono.just(foundCharacter);
                });
    }

    public Mono<Character[]> listCharacters() {
        return jwtUtil
                .getSubject()
                .flatMap(userId -> {
                    Character[] characterList = characterMapper.listCharacters(userId);

                    return Mono.just(characterList);
                });
    }
}
