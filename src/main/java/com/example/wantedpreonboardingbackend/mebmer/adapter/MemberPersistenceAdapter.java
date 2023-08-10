package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.mebmer.application.MemberPersistencePort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class MemberPersistenceAdapter implements MemberPersistencePort {

    private final MemberRepository memberRepository;

    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }
}
