package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.mebmer.application.MemberPersistencePort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class MemberPersistenceAdapter implements MemberPersistencePort {

    private final MemberRepository memberRepository;

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }
}
