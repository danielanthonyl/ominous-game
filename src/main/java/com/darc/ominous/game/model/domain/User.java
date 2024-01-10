package com.darc.ominous.game.model.domain;

import org.apache.ibatis.type.Alias;

@Alias("UserEntity")
public class User {
    public String id;
    public String username;
    public String email;

    public String password;
}
