package com.darc.ominous.game.services;

import com.darc.ominous.game.mappers.UserMapper;
import com.darc.ominous.game.model.domain.User;
import com.darc.ominous.game.utils.JwtUtil;
import com.darc.ominous.game.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private JwtUtil jwtUtil;
    final private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Mono<String> createUser(User user) {
        return Mono.fromCallable(() -> {
            final User foundUser = userMapper.findUserByCredentials(null, user.username, user.email);

            if (!Objects.isNull(foundUser)) {
                throw new BadCredentialsException("Credentials aren't unique.");
            }

            final String id = UUID.randomUUID().toString();
            final String encodedPassword = passwordUtil.encode(user.password);
//            final User newUser = new User(id, user.username, user.username, encodedPassword);

            user.id = id;
            user.password = encodedPassword;
            userMapper.createUser(user);

            return jwtUtil.generate(user.email);
        });
    }

    public Mono<String> createUserSession(User user) {
        String errorMessage = "Unable to find user with provided credentials";

        // verify if user exist on DB
        User foundUser = userMapper.findUserByCredentials(null, null, user.email);

        if (Objects.isNull(foundUser)) {
            //// throw error if it does not exist
            throw new BadCredentialsException(errorMessage);
        }

        // verify if user credentials are correct
        if (!user.email.equals(foundUser.email) || !passwordUtil.compare(user.password, foundUser.password)) {
            // throw error if not correct
            throw new BadCredentialsException(errorMessage);
        }

        // generate jwt
        String jwt = jwtUtil.generate(user.email);

        // respond with jwt
        return Mono.just(jwt);
    }
}
