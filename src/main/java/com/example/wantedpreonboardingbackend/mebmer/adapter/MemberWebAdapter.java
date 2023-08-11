package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.common.annotation.Auth;
import com.example.wantedpreonboardingbackend.common.utils.KeyHolder;
import com.example.wantedpreonboardingbackend.mebmer.application.MemberWebPort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
class MemberWebAdapter {

    private final MemberWebPort memberWebPort;

    @PostMapping
    void save(@Validated @RequestBody Member member) {
        memberWebPort.save(member);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Member member) {
        Long id = memberWebPort.login(member);

        Key key = Keys.hmacShaKeyFor(KeyHolder.KEY);

        String jws = Jwts.builder()
                .setSubject(String.valueOf(id))
                .signWith(key)
                .compact();

        return Map.of("Authentication", jws);
    }

    @PostMapping("/test")
    public void test(@Auth Long memberId) {
        System.out.println(memberId);
    }
}
