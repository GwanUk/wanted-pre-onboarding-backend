package com.example.wantedpreonboardingbackend.twit.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class TwitService implements TwitWebPort{

    private final TwitPersistencePort twitPersistencePort;
}
