package com.darc.ominous.game.utils;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Utils {
    public static Boolean isEmpty(String string) {
        return Objects.isNull(string) || string.isBlank();
    }

    public static Boolean isPositive(Integer number) {
        return number < 0;
    }
}
