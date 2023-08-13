package com.example.wantedpreonboardingbackend.twit.application;

import com.example.wantedpreonboardingbackend.mebmer.application.MemberPersistencePort;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class TwitService implements TwitWebPort{

    private final TwitPersistencePort twitPersistencePort;
    private final MemberPersistencePort memberPersistencePort;

    @Override
    public void save(Long memberId, Twit twit) {
    }
}
