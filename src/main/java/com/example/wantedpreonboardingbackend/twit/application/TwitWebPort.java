package com.example.wantedpreonboardingbackend.twit.application;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TwitWebPort {
    void save(Long memberId, Twit twit);

    Page<Twit> findAllWithMember(Pageable pageable);

    Optional<Twit> findByIdWithMember(Long twitId);

    void update(Long memberId, Long twitId, Twit twit);

    void delete(Long memberId, Long twitId);
}
