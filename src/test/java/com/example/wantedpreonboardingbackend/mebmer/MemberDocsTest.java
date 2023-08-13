package com.example.wantedpreonboardingbackend.mebmer;

import com.example.wantedpreonboardingbackend.mebmer.application.MemberWebPort;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.twit.com", uriPort = "443")
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class MemberDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberWebPort memberWebPort;

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("사용자 회원가입 엔드포인트 테스트")
    void member_signup_endpoint() throws Exception {
        // given
        Member member = new Member("user@naver.com", "user1234");
        String json = objectMapper.writeValueAsString(member);

        // expected
        mockMvc.perform(post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("signup"));
    }

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("사용자 로그인 엔드포인트 테스트")
    void member_login_endpoint() throws Exception {
        // given
        Member member = new Member("user@naver.com", "user1234");
        memberWebPort.save(member);
        String json = objectMapper.writeValueAsString(member);

        // expected
        mockMvc.perform(post("/member/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Authentication").isNotEmpty())
                .andDo(print())
                .andDo(document("login"));
    }
}
