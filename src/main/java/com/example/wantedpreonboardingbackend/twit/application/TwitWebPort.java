package com.example.wantedpreonboardingbackend.twit.application;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TwitWebPort {
    void save(Long memberId, Twit twit);

    List<Twit> findAllWithMember(Pageable pageable);

    Optional<Twit> findById(Long twitId);

    void update(Long memberId, Long twitId, Twit twit);
}
