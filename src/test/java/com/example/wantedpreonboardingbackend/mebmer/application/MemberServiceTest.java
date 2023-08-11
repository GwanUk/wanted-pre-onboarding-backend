package com.example.wantedpreonboardingbackend.mebmer.application;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberPersistencePort memberPersistencePort;

    @Test
    @DisplayName("회원 가입")
    void save() {
        // given
        Member member = new Member("user@naver.com", "user1234");

        // when
        memberService.save(member);

        // then
        then(memberPersistencePort).should().save(member);
    }

    @Test
    @DisplayName("로그인")
    void login() {
        // given
        Member member = new Member("user@naver.com", "user1234");
        given(memberPersistencePort.findByEmail("user@naver.com")).willReturn(Optional.of(member));

        // when
        Long memberId = memberService.login(member);

        // then
        assertThat(memberId).isNull();
    }
}