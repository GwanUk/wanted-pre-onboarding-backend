package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TwitResponse {
    private final Long id;
    private final String content;
    private final String email;

    public static TwitResponse from(Twit twit) {
        return new TwitResponse(
                twit.getId(),
                twit.getContent(),
                twit.getMember().getEmail()
        );
    }
}
