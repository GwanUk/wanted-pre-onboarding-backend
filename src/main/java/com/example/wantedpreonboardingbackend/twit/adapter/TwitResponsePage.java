package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class TwitResponsePage {
    private final Collection<TwitResponse> twitResponses;
    private final long page;
    private final long size;
    private final long totalPage;

    public static TwitResponsePage from(Page<Twit> page) {
        return new TwitResponsePage(
                page.getContent().stream()
                        .map(TwitResponse::from)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );
    }
}
