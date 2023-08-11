package com.example.wantedpreonboardingbackend.common.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;

public abstract class KeyHolder {

    public static byte[] KEY;

    static {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        byte[] encodedKey = key.getEncoded();
        KEY = Base64.getEncoder().encode(encodedKey);
    }
}
