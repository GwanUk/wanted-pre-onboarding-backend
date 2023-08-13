package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.common.annotation.Auth;
import com.example.wantedpreonboardingbackend.twit.application.TwitWebPort;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/twit")
@RequiredArgsConstructor
class TwitWebAdapter {

    private final TwitWebPort twitWebPort;

    @PostMapping
    void save(@Auth Long memberId, @RequestBody Twit twit) {
        twitWebPort.save(memberId, twit);
    }
}
