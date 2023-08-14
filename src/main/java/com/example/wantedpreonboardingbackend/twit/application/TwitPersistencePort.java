package com.example.wantedpreonboardingbackend.twit.application;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TwitPersistencePort {

    Page<Twit> findAllWithMember(Pageable pageable);

    Optional<Twit> findByIdWithMember(Long twitId);

    void save(Twit twit);

    void delete(Twit twit);
}
