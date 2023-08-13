package com.example.wantedpreonboardingbackend.mebmer.application;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberPersistencePort {
    List<Member> findAll();

    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Long memberId);

    void save(Member member);
}
