package com.example.wantedpreonboardingbackend.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

public abstract class JwtContext {

    private static final byte[] KEY;

    static {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        byte[] encodedKey = key.getEncoded();
        KEY = Base64.getEncoder().encode(encodedKey);
    }

    public static String createJwt(Long id) {
        Key secretKey = Keys.hmacShaKeyFor(KEY);

        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .compact();
    }

    public static String getSubject(String jwt) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(jwt);

        return claims.getBody().getSubject();
    }
}
