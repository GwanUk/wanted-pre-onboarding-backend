package com.example.wantedpreonboardingbackend.twit.domain;

import com.example.wantedpreonboardingbackend.common.exception.UnauthorizedException;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TwitTest {

    @Mock
    private Member member;
    
    @Test
    @DisplayName("작성자 확인")
    void is_writer() {
        // given
        Twit twit = new Twit("writing test");
        given(member.getId()).willReturn(1L);
        twit.setMember(member);

        // when
        twit.isWriter(1L);
    }

    @Test
    @DisplayName("작성자 달라서 예외 발생")
    void is_writer_exception() {
        // given
        Twit twit = new Twit("writing test");
        given(member.getId()).willReturn(1L);
        twit.setMember(member);

        // when
        Assertions.assertThatThrownBy(() -> twit.isWriter(2L))
                .isInstanceOf(UnauthorizedException.class);
    }
}