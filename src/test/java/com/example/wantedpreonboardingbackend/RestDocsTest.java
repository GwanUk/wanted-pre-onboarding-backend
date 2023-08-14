package com.example.wantedpreonboardingbackend;

import com.example.wantedpreonboardingbackend.common.utils.JwtContext;
import com.example.wantedpreonboardingbackend.mebmer.adapter.MemberRequest;
import com.example.wantedpreonboardingbackend.mebmer.application.MemberWebPort;
import com.example.wantedpreonboardingbackend.twit.adapter.TwitRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "15.165.27.4")
@ExtendWith(RestDocumentationExtension.class)
public class RestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberWebPort memberWebPort;

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("사용자 회원가입 엔드포인트")
    void signup() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("user@naver.com", "user1234");
        String json = objectMapper.writeValueAsString(memberRequest);

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.post("/member")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("signup",
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        )
                ));
    }

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("사용자 로그인 엔드포인트")
    void login() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("user@naver.com", "user1234");
        memberWebPort.save(memberRequest.toEntity());
        String json = objectMapper.writeValueAsString(memberRequest);

        // expected
        mockMvc.perform(post("/member/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Authentication").isNotEmpty())
                .andDo(print())
                .andDo(document("login",
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("Authentication").description("JWT 인증 토큰")
                        )
                ));
    }

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("새로운 게시글을 생성하는 엔드포인트")
    void save() throws Exception {
        // given
        TwitRequest twitRequest = new TwitRequest("test writing");
        String json = objectMapper.writeValueAsString(twitRequest);

        // expected
        mockMvc.perform(post("/twit")
                        .header("Authentication", JwtContext.createJwt(1L))
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("save",
                        requestHeaders(
                                headerWithName("Authentication").description("JWT 인증 토큰")
                        ),
                        requestFields(
                                fieldWithPath("content").description("게시글 내용")
                        )
                ));
    }

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("게시글 목록을 조회하는 엔드포인트")
    void findAll() throws Exception {
        // expected
        mockMvc.perform(get("/twit?page=0&size=10")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("findAll",
                        requestParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 크기")
                        ),
                        responseFields(
                                fieldWithPath("twitResponses[].id").description("게시글 ID"),
                                fieldWithPath("twitResponses[].content").description("게시글 내용"),
                                fieldWithPath("twitResponses[].email").description("게시글 작성자 email"),
                                fieldWithPath("page").description("페이지 번호"),
                                fieldWithPath("size").description("페이지 크기"),
                                fieldWithPath("totalPage").description("전체 페이지")
                        )

                ));
    }

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("특정 게시글을 조회하는 엔드포인트")
    void find() throws Exception {
        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.get("/twit/{twitId}", "1")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("find",
                        pathParameters(
                                parameterWithName("twitId").description("게시글 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글 ID"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("email").description("게시글 작성자 email")
                        )
                ));
    }

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("특정 게시글을 수정하는 엔드포인트")
    void update() throws Exception {
        // given
        TwitRequest twitRequest = new TwitRequest("test writing");
        String json = objectMapper.writeValueAsString(twitRequest);

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.put("/twit/{twitId}", "1")
                        .header("Authentication", JwtContext.createJwt(1L))
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update",
                        requestHeaders(
                                headerWithName("Authentication").description("JWT 인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("twitId").description("게시글 ID")
                        ),
                        requestFields(
                                fieldWithPath("content").description("게시글 내용")
                        )
                ));

    }

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("특정 게시글을 삭제하는 엔드포인트")
    void delete() throws Exception {
        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/twit/{twitId}", "1")
                        .header("Authentication", JwtContext.createJwt(1L)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("delete",
                        requestHeaders(
                                headerWithName("Authentication").description("JWT 인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("twitId").description("게시글 ID")
                        )
                ));
    }
}
