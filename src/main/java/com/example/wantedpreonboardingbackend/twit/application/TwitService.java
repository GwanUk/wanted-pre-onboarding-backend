package com.example.wantedpreonboardingbackend.twit.application;

import com.example.wantedpreonboardingbackend.common.exception.NotFoundDataException;
import com.example.wantedpreonboardingbackend.mebmer.application.MemberPersistencePort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
class TwitService implements TwitWebPort{

    private final TwitPersistencePort twitPersistencePort;
    private final MemberPersistencePort memberPersistencePort;

    @Override
    public List<Twit> findAllWithMember(Pageable pageable) {
        return twitPersistencePort.findAllWithMember(pageable);
    }

    @Override
    public Optional<Twit> findById(Long twitId) {
        return twitPersistencePort.findById(twitId);
    }

    @Override
    public void save(Long memberId, Twit twit) {
        Member member = memberPersistencePort.findById(memberId)
                .orElseThrow(NotFoundDataException::new);

        twit.setMember(member);
        twitPersistencePort.save(twit);
    }
}
