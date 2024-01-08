package com.darc.ominous.game.services;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.darc.ominous.game.mappers.UserMapper;
import com.darc.ominous.game.model.domain.User;
import com.darc.ominous.game.utils.JwtUtil;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private JwtUtil jwtUtil;
    private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Mono<String> createUser(User user) {
        return Mono.fromCallable(() -> {
            // Check if user with the same email already exists (non-blocking operation)
            User foundUser = userMapper.findUserByCredentials(null, user.username(), user.email());
            System.out.println("is unique " + foundUser.email());

            if (!Objects.isNull(foundUser)) {
                throw new BadCredentialsException("Credentials aren't unique.");
            }

            // Generate a new user and save to the database
            String id = UUID.randomUUID().toString();
            User newUser = new User(id, user.username(), user.email(), user.password());
            userMapper.createUser(newUser);

            // Generate a token and return it
            return jwtUtil.generate(user.username());
        });
    }

    public Mono<String> createUserSession(User user) {
        // verify if user exist on DB
        //// throw error if does not exists
        // verify if user's credentials are correct
        //// throw error if not correct
        // generate jwt
        // respond with jwt
        return Mono.just("");
    }
}
