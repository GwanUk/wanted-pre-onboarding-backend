package com.example.wantedpreonboardingbackend.twit.application;

import com.example.wantedpreonboardingbackend.common.exception.NotFoundDataException;
import com.example.wantedpreonboardingbackend.mebmer.application.MemberPersistencePort;
import com.example.wantedpreonboardingbackend.mebmer.application.MemberWebPort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
class TwitService implements TwitWebPort{

    private final TwitPersistencePort twitPersistencePort;
    private final MemberWebPort memberWebPort;

    @Override
    @Transactional(readOnly = true)
    public List<Twit> findAllWithMember(Pageable pageable) {
        return twitPersistencePort.findAllWithMember(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Twit> findById(Long twitId) {
        return twitPersistencePort.findById(twitId);
    }

    @Override
    public void save(Long memberId, Twit twit) {
        Member member = memberWebPort.findById(memberId)
                .orElseThrow(NotFoundDataException::new);

        twit.setMember(member);
        twitPersistencePort.save(twit);
    }

    @Override
    public void update(Long memberId, Long twitId, Twit twit) {
        Twit findTwit = twitPersistencePort.findByIdWithMember(twitId)
                .orElseThrow(NotFoundDataException::new);

        findTwit.isWriter(memberId);

        findTwit.rewrite(twit.getContent());
    }

    @Override
    public void delete(Long memberId, Long twitId) {
        Twit findTwit = twitPersistencePort.findByIdWithMember(twitId)
                .orElseThrow(NotFoundDataException::new);

        findTwit.isWriter(memberId);

        twitPersistencePort.delete(findTwit);
    }
}
