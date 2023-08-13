package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.twit.application.TwitPersistencePort;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class TwitPersistenceAdapter implements TwitPersistencePort {

    private final TwitRepository twitRepository;

    @Override
    public List<Twit> findAllWithMember(Pageable pageable) {
        return twitRepository.findAllWithMember(pageable);
    }

    @Override
    public Optional<Twit> findById(Long twitId) {
        return twitRepository.findById(twitId);
    }

    @Override
    public void save(Twit twit) {
        twitRepository.save(twit);
    }
}
