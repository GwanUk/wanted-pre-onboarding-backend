package com.example.wantedpreonboardingbackend.twit.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class TwitPersistenceAdapter {

    private final TwitRepository twitRepository;
}
