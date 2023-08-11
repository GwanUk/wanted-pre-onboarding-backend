package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.mebmer.application.MemberPersistencePort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class MemberPersistenceAdapter implements MemberPersistencePort {

    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(Member member) {
//        member.passwordEncoding(passwordEncoder);
        memberRepository.save(member);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
