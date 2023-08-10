package com.example.wantedpreonboardingbackend.mebmer.application;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberPersistencePort memberPersistencePort;

    @Test
    @DisplayName("회원 가입")
    void save_password_encoding() {
        // given
        Member member = new Member("user@naver.com", "user1234");

        // when
        memberService.save(member);

        // then
        BDDMockito.then(memberPersistencePort).should().save(member);
    }
}