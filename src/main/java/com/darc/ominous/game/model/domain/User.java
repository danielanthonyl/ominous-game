package com.darc.ominous.game.model.domain;

import org.apache.ibatis.type.Alias;

@Alias("User")
public record User(String id, String username, String email, String password) {
}
