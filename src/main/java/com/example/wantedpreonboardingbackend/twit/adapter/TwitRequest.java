package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class TwitRequest {
    private final String content;

    @JsonCreator
    public TwitRequest(String content) {
        this.content = content;
    }

    public Twit toEntity() {
        return new Twit(content);
    }
}
