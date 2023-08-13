package com.example.wantedpreonboardingbackend.mebmer.domain;

import com.example.wantedpreonboardingbackend.common.utils.PasswordEncoderUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    @DisplayName("비밀번호 인코딩 확인")
    void password_encoding() {
        // given
        Member member = new Member("user@naver.com", "user1234");

        // when
        member.passwordEncoding();

        // then
        assertThat(PasswordEncoderUtil.matches("user1234", member.getPassword())).isTrue();
    }

    @Test
    @DisplayName("비밀번호 인코딩 매치 확인")
    void password_encoding_match() {
        // given
        Member member = new Member("user@naver.com", "user1234");
        Member findMember = new Member("user@naver.com", PasswordEncoderUtil.encode("user1234"));

        // expected
        assertThat(findMember.matchPassword(member)).isTrue();
    }
}