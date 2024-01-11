package com.darc.ominous.game.services;

import com.darc.ominous.game.mappers.CharacterMapper;
import com.darc.ominous.game.mappers.UserMapper;
import com.darc.ominous.game.model.domain.Character;
import com.darc.ominous.game.model.domain.User;
import com.darc.ominous.game.model.domain.inputs.FindCharacterInput;
import com.darc.ominous.game.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

@Service
public class CharacterService {
    @Autowired
    CharacterMapper characterMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtil jwtUtil;

    public Mono<Character> createCharacter(Character character) {
        return jwtUtil
                .getSubject()
                .flatMap(userEmail -> {
                    Character foundCharacter = characterMapper.findCharacter(null, character.name);

                    if (!Objects.isNull(foundCharacter)) {
                        throw new RuntimeException("Character name already taken.");
                    }

                    User foundUser = userMapper.findUserByCredentials(null, null, userEmail);

                    if (Objects.isNull(foundUser)) {
                        throw new BadCredentialsException("Unauthorized.");
                    }

                    character.setUserId(foundUser.id);
                    characterMapper.createCharacter(character);
                    Character newCharacter = characterMapper.findCharacter(character.id, character.name);

                    return Mono.just(newCharacter);
                });
    }

    public Mono<Character> findCharacter(FindCharacterInput findCharacterInput) {
        return Mono.defer(() -> {
            Character foundCharacter = characterMapper.findCharacter(findCharacterInput.characterId,
                    findCharacterInput.characterName);

            if (foundCharacter == null) {
                String errorMessage = "Couldn't find a character with name or id: " +
                        Optional.ofNullable(findCharacterInput.characterId).orElse(findCharacterInput.characterName);

                return Mono.error(new RuntimeException(errorMessage));
            }

            return Mono.just(foundCharacter);
        });
    }
}
