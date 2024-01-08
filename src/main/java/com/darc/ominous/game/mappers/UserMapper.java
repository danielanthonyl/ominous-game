package com.darc.ominous.game.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.darc.ominous.game.model.domain.User;

@Mapper
public interface UserMapper {
    void createUser(User user);

    User findUserById(String id);

    User findUserByCredentials(String id, String username, String email);
}