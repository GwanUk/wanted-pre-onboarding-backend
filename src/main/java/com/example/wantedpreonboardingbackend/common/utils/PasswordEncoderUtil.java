package com.example.wantedpreonboardingbackend.common.utils;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class PasswordEncoderUtil {

    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
