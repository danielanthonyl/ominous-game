package com.darc.ominous.game.services;

import com.darc.ominous.game.mappers.CharacterMapper;
import com.darc.ominous.game.mappers.UserMapper;
import com.darc.ominous.game.model.domain.Character;
import com.darc.ominous.game.model.domain.User;
import com.darc.ominous.game.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class CharacterService {
    @Autowired
    CharacterMapper characterMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtil jwtUtil;

    public Mono<Character> createCharacter(Character character) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    try {
                        String token = authentication.getPrincipal().toString();
                        return jwtUtil.getSubject(token);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to extract user ID from token", e);
                    }
                })
                .flatMap(userEmail -> {
                    User foundUser = userMapper.findUserByCredentials(null, null, userEmail);

                    if (Objects.isNull(foundUser)) {
                        throw new BadCredentialsException("Unauthorized.");
                    }

                    character.setUserId(foundUser.id);
                    characterMapper.createCharacter(character);

                    return Mono.just(character);
                });
    }
}
