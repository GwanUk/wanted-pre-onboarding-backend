package com.example.wantedpreonboardingbackend.twit.application;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;

public interface TwitWebPort {
    void save(Long memberId, Twit twit);
}
