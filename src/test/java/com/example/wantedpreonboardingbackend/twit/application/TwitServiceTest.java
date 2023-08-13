package com.example.wantedpreonboardingbackend.twit.application;

import com.example.wantedpreonboardingbackend.mebmer.application.MemberPersistencePort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TwitServiceTest {

    @InjectMocks
    private TwitService twitService;

    @Mock
    private TwitPersistencePort twitPersistencePort;

    @Mock
    private MemberPersistencePort memberPersistencePort;

    @Test
    @DisplayName("게시글 작성")
    void save() {
        // given
        Long memberId = 1L;
        Member member = new Member("user@naver.com", "user1234");
        Twit twit = new Twit("test writing");
        given(memberPersistencePort.findById(ArgumentMatchers.eq(1L))).willReturn(Optional.of(member));

        // when
        twitService.save(memberId, twit);

        // then
        ArgumentCaptor<Twit> ac = ArgumentCaptor.forClass(Twit.class);
        then(twitPersistencePort).should().save(ac.capture());
        assertThat(ac.getValue().getContent()).isEqualTo("test writing");
        assertThat(ac.getValue().getMember().getEmail()).isEqualTo("user@naver.com");
        assertThat(ac.getValue().getMember().getPassword()).isEqualTo("user1234");
    }
}