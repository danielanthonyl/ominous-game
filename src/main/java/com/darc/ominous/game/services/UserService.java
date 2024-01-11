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
    final private UserMapper userMapper;
    @Autowired
    private PasswordUtil passwordUtil;
    @Autowired
    private JwtUtil jwtUtil;

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

            String id = UUID.randomUUID().toString();
            final String encodedPassword = passwordUtil.encode(user.password);

            user.id = id;
            user.password = encodedPassword;
            userMapper.createUser(user);

            return jwtUtil.generate(id);
        });
    }

    public Mono<String> createUserSession(User user) {
        return Mono.fromCallable(() -> {
            String errorMessage = "Unable to find user with provided credentials";

            User foundUser = userMapper.findUserByCredentials(null, null, user.email);

            if (Objects.isNull(foundUser)) {
                throw new BadCredentialsException(errorMessage);
            }

            if (!user.email.equals(foundUser.email) || !passwordUtil.compare(user.password, foundUser.password)) {
                throw new BadCredentialsException(errorMessage);
            }

            return jwtUtil.generate(foundUser.id);
        });
    }
}
