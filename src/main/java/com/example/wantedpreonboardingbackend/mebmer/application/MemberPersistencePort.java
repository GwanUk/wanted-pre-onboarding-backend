package com.example.wantedpreonboardingbackend.mebmer.application;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;

import java.util.List;

public interface MemberPersistencePort {
    void save(Member member);
}
