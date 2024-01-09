package com.darc.ominous.game.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
    @Autowired
    PasswordEncoder passwordEncoder;

    public final String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public final Boolean compare(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
