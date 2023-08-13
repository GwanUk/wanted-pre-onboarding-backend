package com.example.wantedpreonboardingbackend.twit.application;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;

import java.util.List;

public interface TwitPersistencePort {

    List<Twit> findAllWithMember();

    void save(Twit twit);
}
