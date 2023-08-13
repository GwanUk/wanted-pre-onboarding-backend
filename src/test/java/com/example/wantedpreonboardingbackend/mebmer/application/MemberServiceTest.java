package com.example.wantedpreonboardingbackend.mebmer.application;

import com.example.wantedpreonboardingbackend.common.utils.PasswordEncoderUtil;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberPersistencePort memberPersistencePort;

    @Test
    @DisplayName("회원 가입 패스워드 암호화")
    void save() {
        // given
        Member member = new Member("user@naver.com", "user1234");

        // when
        memberService.save(member);

        // then
        ArgumentCaptor<Member> ac = ArgumentCaptor.forClass(Member.class);
        then(memberPersistencePort).should().save(ac.capture());
        assertThat(ac.getValue().getEmail()).isEqualTo("user@naver.com");
        assertThat(PasswordEncoderUtil.matches("user1234", ac.getValue().getPassword())).isTrue();
    }

    @Test
    @DisplayName("로그인")
    void login() {
        // given
        Member member = new Member("user@naver.com", "user1234");
        Member findMember = new Member("user@naver.com", PasswordEncoderUtil.encode("user1234"));
        given(memberPersistencePort.findByEmail("user@naver.com")).willReturn(Optional.of(findMember));

        // when
        Long memberId = memberService.login(member);

        // then
        assertThat(memberId).isNull();
    }
}