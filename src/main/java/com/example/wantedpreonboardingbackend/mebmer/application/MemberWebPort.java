package com.example.wantedpreonboardingbackend.mebmer.application;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;

import java.util.Optional;

public interface MemberWebPort {

    void save(Member member);

    Long login(Member member);
}
