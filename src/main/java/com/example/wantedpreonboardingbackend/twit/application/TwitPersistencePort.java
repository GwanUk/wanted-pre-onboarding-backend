package com.example.wantedpreonboardingbackend.twit.application;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TwitPersistencePort {

    List<Twit> findAllWithMember(Pageable pageable);

    void save(Twit twit);

    Optional<Twit> findById(Long twitId);
}