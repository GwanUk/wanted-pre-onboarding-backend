package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.common.utils.JwtContext;
import com.example.wantedpreonboardingbackend.mebmer.application.MemberWebPort;
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
    void save(@Validated @RequestBody MemberRequest memberRequest) {
        memberWebPort.save(memberRequest.toEntity());
    }

    @PostMapping("/login")
    Map<String, String> login(@Validated @RequestBody MemberRequest memberRequest) {
        Long memberId = memberWebPort.login(memberRequest.toEntity());
        String jwt = JwtContext.createJwt(memberId);
        return Map.of("Authentication", jwt);
    }
}
