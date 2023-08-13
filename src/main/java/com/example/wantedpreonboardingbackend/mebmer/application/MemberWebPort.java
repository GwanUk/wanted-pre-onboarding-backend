package com.example.wantedpreonboardingbackend.mebmer.application;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;

public interface MemberWebPort {

    void save(Member member);

    Long login(Member member);
}
