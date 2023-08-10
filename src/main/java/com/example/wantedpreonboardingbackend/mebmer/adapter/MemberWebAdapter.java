package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.mebmer.application.MemberWebPort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
class MemberWebAdapter {

    private final MemberWebPort memberWebPort;

    @PostMapping
    void save(@Validated @RequestBody Member member) {
        memberWebPort.save(member);
    }
}
