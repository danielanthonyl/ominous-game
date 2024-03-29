package com.darc.ominous.game.mappers;

import com.darc.ominous.game.model.domain.Character;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CharacterMapper {
    void createCharacter(Character character);

    Character findCharacter(String userId, String id, String name);

    Character[] listCharacters(String userId);
}
