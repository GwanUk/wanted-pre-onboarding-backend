package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.mebmer.application.port.MemberWebPort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberWebAdapter.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
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
        Member member = new Member("user@naver", "12345678");
        String json = objectMapper.writeValueAsString(member);

        // when
        mockMvc.perform(post("/members")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        BDDMockito.then(memberWebPort).should().save(member);
    }

    @Test
    @DisplayName("회원 가입 이메일 @ 벨리데이션 확인")
    void save_email_validation() throws Exception {
        // given
        Member member = new Member("naver.com", "12345678");
        String json = objectMapper.writeValueAsString(member);

        // expected
        mockMvc.perform(post("/members")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 가입 비밀번호 8자 이상 벨리데이션 확인")
    void save_password_validation() throws Exception {
        // given
        Member member = new Member("user@naver", "1234567");
        String json = objectMapper.writeValueAsString(member);

        // expected
        mockMvc.perform(post("/members")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}