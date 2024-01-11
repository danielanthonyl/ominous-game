package com.darc.ominous.game.model.domain;

import com.darc.ominous.game.enums.CharacterOccupations;
import com.darc.ominous.game.utils.Utils;
import org.apache.ibatis.type.Alias;
import org.springframework.security.authentication.BadCredentialsException;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Alias("CharacterEntity")
public class Character {

    public String id;
    public String userId;
    public String name;
    public String occupation;
    public int health;
    public int mana;
    public int experience;
    public int level;
    public Date createdAt;
    public int strength;
    public int vitality;
    public int dexterity;
    public int intelligence;

    public Character() {
        this.id = UUID.randomUUID().toString();
    }

    public static Mono<Character> validateOccupation(Character character) throws IllegalArgumentException {
        character.occupation = character.occupation.toUpperCase();
        String errorMessage = "Invalid occupation: " + character.occupation;

        try {
            CharacterOccupations occupationEnum = CharacterOccupations.valueOf(character.occupation);

            if (occupationEnum.equals(CharacterOccupations.MONK) ||
                    occupationEnum.equals(CharacterOccupations.WARRIOR) ||
                    occupationEnum.equals(CharacterOccupations.WIZARD) ||
                    occupationEnum.equals(CharacterOccupations.ASSASSIN) ||
                    occupationEnum.equals(CharacterOccupations.HEALER) ||
                    occupationEnum.equals(CharacterOccupations.TAMER)) {
                return Mono.just(character);
            } else {
                throw new IllegalArgumentException(errorMessage);
            }
        } catch (IllegalArgumentException error) {
            throw new IllegalArgumentException(errorMessage, error.getCause());
        }
    }

    public static Mono<Character> validateCharacterFields(Character character) throws BadCredentialsException {
        if (Utils.isEmpty(character.name) || Utils.isEmpty(character.occupation)) {
            throw new BadCredentialsException("All character fields are required.");
        }

        return Mono.just(character);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}