package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.common.annotation.Auth;
import com.example.wantedpreonboardingbackend.twit.application.TwitWebPort;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/twit")
@RequiredArgsConstructor
class TwitWebAdapter {

    private final TwitWebPort twitWebPort;

    @GetMapping
    Page<Twit> findAllWithMember(@PageableDefault(sort = "id") Pageable pageable) {
        return twitWebPort.findAllWithMember(pageable);
    }

    @GetMapping("/{twitId}")
    Optional<Twit> findByIdWithMember(@PathVariable Long twitId) {
        return twitWebPort.findByIdWithMember(twitId);
    }

    @PostMapping
    void save(@Auth Long memberId, @RequestBody Twit twit) {
        twitWebPort.save(memberId, twit);
    }

    @PutMapping("/{twitId}")
    void update(@Auth Long memberId, @PathVariable Long twitId, @RequestBody Twit twit) {
        twitWebPort.update(memberId, twitId, twit);
    }

    @DeleteMapping("/{twitId}")
    void delete(@Auth Long memberId, @PathVariable Long twitId) {
        twitWebPort.delete(memberId, twitId);
    }
}
