package com.example.wantedpreonboardingbackend.mebmer.application;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;

import java.util.Optional;

public interface MemberPersistencePort {
    void save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);
}
