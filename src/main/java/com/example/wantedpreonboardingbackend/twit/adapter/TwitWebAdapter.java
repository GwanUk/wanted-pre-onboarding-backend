package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.common.annotation.Auth;
import com.example.wantedpreonboardingbackend.twit.application.TwitWebPort;
import lombok.RequiredArgsConstructor;
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
    TwitResponsePage findAllWithMember(@PageableDefault Pageable pageable) {
        return TwitResponsePage.from(twitWebPort.findAllWithMember(pageable));

    }

    @GetMapping("/{twitId}")
    Optional<TwitResponse> findByIdWithMember(@PathVariable Long twitId) {
        return twitWebPort.findByIdWithMember(twitId)
                .map(TwitResponse::from);
    }

    @PostMapping
    void save(@Auth Long memberId, @RequestBody TwitRequest twitRequest) {
        twitWebPort.save(memberId, twitRequest.toEntity());
    }

    @PutMapping("/{twitId}")
    void update(@Auth Long memberId, @PathVariable Long twitId, @RequestBody TwitRequest twitRequest) {
        twitWebPort.update(memberId, twitId, twitRequest.toEntity());
    }

    @DeleteMapping("/{twitId}")
    void delete(@Auth Long memberId, @PathVariable Long twitId) {
        twitWebPort.delete(memberId, twitId);
    }
}
