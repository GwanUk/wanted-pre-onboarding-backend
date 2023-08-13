package com.example.wantedpreonboardingbackend.mebmer.application;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;

import java.util.Optional;

public interface MemberWebPort {

    Optional<Member> findById(Long memberId);

    void save(Member member);

    Long login(Member member);
}
