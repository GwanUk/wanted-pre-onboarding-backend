package com.example.wantedpreonboardingbackend.mebmer.application;

import com.example.wantedpreonboardingbackend.common.exception.NotFoundDataException;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
class MemberService implements MemberWebPort{

    private final MemberPersistencePort memberPersistencePort;

    @Override
    public Optional<Member> findById(Long memberId) {
        return memberPersistencePort.findById(memberId);
    }

    @Override
    public void save(Member member) {
        Member encodedMember = member.passwordEncoding();
        memberPersistencePort.save(encodedMember);
    }

    @Override
    @Transactional(readOnly = true)
    public Long login(Member member) {
        Member findMember = memberPersistencePort.findByEmail(member.getEmail())
                .orElseThrow(NotFoundDataException::new);

        findMember.matchPassword(member);

        return findMember.getId();
    }
}
