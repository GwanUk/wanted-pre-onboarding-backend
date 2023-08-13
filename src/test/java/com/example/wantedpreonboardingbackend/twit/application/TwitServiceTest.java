package com.example.wantedpreonboardingbackend.twit.application;

import com.example.wantedpreonboardingbackend.mebmer.application.MemberWebPort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TwitServiceTest {

    @InjectMocks
    private TwitService twitService;

    @Mock
    private TwitPersistencePort twitPersistencePort;

    @Mock
    private MemberWebPort memberWebPort;

    @Test
    @DisplayName("게시글 작성")
    void save() {
        // given
        Long memberId = 1L;
        Member member = new Member("user@naver.com", "user1234");
        Twit twit = new Twit("test writing");
        given(memberWebPort.findById(eq(1L))).willReturn(Optional.of(member));

        // when
        twitService.save(memberId, twit);

        // then
        ArgumentCaptor<Twit> ac = ArgumentCaptor.forClass(Twit.class);
        then(twitPersistencePort).should().save(ac.capture());
        assertThat(ac.getValue().getContent()).isEqualTo("test writing");
        assertThat(ac.getValue().getMember().getEmail()).isEqualTo("user@naver.com");
        assertThat(ac.getValue().getMember().getPassword()).isEqualTo("user1234");
    }

    @Test
    @DisplayName("게시글 수정")
    void update() {
        // given
        Long memberId = 1L;
        Long twitId = 2L;
        Twit twit = new Twit("writing test");
        Twit findTwit = Mockito.mock(Twit.class);
        given(twitPersistencePort.findByIdWithMember(eq(twitId)))
                .willReturn(Optional.of(findTwit));

        // when
        twitService.update(memberId, twitId, twit);

        // then
        then(findTwit).should().isWriter(eq(memberId));
        then(findTwit).should().rewrite(eq("writing test"));
    }

    @Test
    @DisplayName("게시글 삭제")
    void delete() {
        // given
        Long memberId = 1L;
        Long twitId = 2L;
        Twit twit = new Twit("writing test");
        Twit findTwit = Mockito.mock(Twit.class);
        given(twitPersistencePort.findByIdWithMember(eq(twitId)))
                .willReturn(Optional.of(findTwit));

        // when
        twitService.delete(memberId, twitId);

        // then
        then(findTwit).should().isWriter(eq(memberId));
        then(twitPersistencePort).should().delete(findTwit);
    }
}