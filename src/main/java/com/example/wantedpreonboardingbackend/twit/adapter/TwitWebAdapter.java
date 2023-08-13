package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.common.annotation.Auth;
import com.example.wantedpreonboardingbackend.twit.application.TwitWebPort;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/twit")
@RequiredArgsConstructor
class TwitWebAdapter {

    private final TwitWebPort twitWebPort;

    @GetMapping
    List<Twit> findAll(@PageableDefault(sort = "id") Pageable pageable) {
        return twitWebPort.findAllWithMember(pageable);
    }

    @PostMapping
    void save(@Auth Long memberId, @RequestBody Twit twit) {
        twitWebPort.save(memberId, twit);
    }
}
