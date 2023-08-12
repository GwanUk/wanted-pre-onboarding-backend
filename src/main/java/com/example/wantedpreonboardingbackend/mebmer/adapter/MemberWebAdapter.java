package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.common.utils.JwtContext;
import com.example.wantedpreonboardingbackend.mebmer.application.MemberWebPort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        String jwt = JwtContext.createJwt(id);
        return Map.of("Authentication", jwt);
    }
}
