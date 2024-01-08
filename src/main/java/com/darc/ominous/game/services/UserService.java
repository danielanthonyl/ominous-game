package com.darc.ominous.game.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.darc.ominous.game.mappers.UserMapper;
import com.darc.ominous.game.model.domain.User;
import com.darc.ominous.game.utils.Response;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Mono<Response> createUser(User user) {
        String id = UUID.randomUUID().toString();

        User newUser = new User(id, user.username(), user.email(), user.password());
        userMapper.createUser(newUser);
        Response response = new Response("User created successfully.", null);

        return Mono.just(response);
    }

    public Mono<Response> createSession(User user) {
        Response response = new Response("User created successfully.", null);

        return Mono.just(response);
    }
}
