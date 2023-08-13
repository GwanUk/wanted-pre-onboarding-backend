package com.example.wantedpreonboardingbackend.twit.application;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TwitWebPort {
    void save(Long memberId, Twit twit);

    List<Twit> findAllWithMember(Pageable pageable);
}
