package com.example.wantedpreonboardingbackend.mebmer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberTest {

    @Test
    @DisplayName("비밀번호 인코딩 확인")
    void password_encoding() {
        // given
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        Member member = new Member("user@naver.com", "user1234");

        // when
        member.passwordEncoding(passwordEncoder);

        // then
        passwordEncoder.matches("user1234", member.getPassword());
    }

}