package com.example.wantedpreonboardingbackend.mebmer.application;

import com.example.wantedpreonboardingbackend.common.exception.NotFoundDataException;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class MemberService implements MemberWebPort{

    private final MemberPersistencePort memberPersistencePort;

    @Override
    public void save(Member member) {
        member.passwordEncoding();
        memberPersistencePort.save(member);
    }

    @Override
    public Long login(Member member) {
        Member findMember = memberPersistencePort.findByEmail(member.getEmail())
                .orElseThrow(NotFoundDataException::new);

        if (!findMember.matchPassword(member)) {
            throw new NotFoundDataException();
        }

        return findMember.getId();
    }
}
