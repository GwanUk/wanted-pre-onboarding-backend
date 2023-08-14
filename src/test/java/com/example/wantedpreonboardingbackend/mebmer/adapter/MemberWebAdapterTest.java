package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.mebmer.application.MemberWebPort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberWebAdapter.class)
class MemberWebAdapterTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberWebPort memberWebPort;

    @Test
    @DisplayName("회원 가입")
    void save() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("user@naver", "12345678");
        String json = objectMapper.writeValueAsString(memberRequest);

        // when
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        ArgumentCaptor<Member> ac = ArgumentCaptor.forClass(Member.class);
        then(memberWebPort).should().save(ac.capture());
        assertThat(ac.getValue().getEmail()).isEqualTo("user@naver");
        assertThat(ac.getValue().getPassword()).isEqualTo("12345678");
    }

    @Test
    @DisplayName("회원 가입 이메일 @ 벨리데이션 확인")
    void save_email_validation() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("naver.com", "12345678");
        String json = objectMapper.writeValueAsString(memberRequest);

        // expected
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("유효성 예외입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 가입 비밀번호 8자 이상 벨리데이션 확인")
    void save_password_validation() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("user@naver", "1234567");
        String json = objectMapper.writeValueAsString(memberRequest);

        // expected
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("유효성 예외입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("user@naver.com", "user1234");
        String json = objectMapper.writeValueAsString(memberRequest);
        given(memberWebPort.login(any())).willReturn(1L);

        // when
        mockMvc.perform(post("/member/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Authentication").isNotEmpty())
                .andDo(print());

        // then
        ArgumentCaptor<Member> ac = ArgumentCaptor.forClass(Member.class);
        then(memberWebPort).should().login(ac.capture());
        assertThat(ac.getValue().getEmail()).isEqualTo("user@naver.com");
        assertThat(ac.getValue().getPassword()).isEqualTo("user1234");
    }

    @Test
    @DisplayName("로그인 이메일 @ 벨리데이션 확인")
    void login_email_validation() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("naver.com", "user1234");
        String json = objectMapper.writeValueAsString(memberRequest);
        given(memberWebPort.login(any())).willReturn(1L);

        // expected
        mockMvc.perform(post("/member/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("유효성 예외입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 비밀번호 8자 이상 벨리데이션 확인")
    void login_password_validation() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("user@naver.com", "user123");
        String json = objectMapper.writeValueAsString(memberRequest);
        given(memberWebPort.login(any())).willReturn(1L);

        // expected
        mockMvc.perform(post("/member/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("유효성 예외입니다."))
                .andDo(print());
    }
}