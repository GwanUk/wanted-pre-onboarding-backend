package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.common.utils.JwtContext;
import com.example.wantedpreonboardingbackend.twit.application.TwitWebPort;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TwitWebAdapter.class)
class TwitWebAdapterTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TwitWebPort twitWebPort;

    @Test
    @DisplayName("게시글 전체 조회")
    void findAllWithMember() throws Exception {
        // given
        given(twitWebPort.findAllWithMember(any())).willReturn(Page.empty());

        // when
        mockMvc.perform(get("/twit")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        PageRequest pageRequest = PageRequest.of(0, 10);
        then(twitWebPort).should().findAllWithMember(pageRequest);
    }

    @Test
    @DisplayName("게시글 조회 by id")
    void find_by_id() throws Exception {
        // given
        given(twitWebPort.findByIdWithMember(any())).willReturn(Optional.empty());

        // when
        mockMvc.perform(get("/twit/{twitId}", "1")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        then(twitWebPort).should().findByIdWithMember(eq(1L));
    }

    @Test
    @DisplayName("게시글 생성")
    void save() throws Exception {
        // given
        TwitRequest twitRequest = new TwitRequest("test writing");
        String json = objectMapper.writeValueAsString(twitRequest);

        // when
        mockMvc.perform(post("/twit")
                        .header("Authentication", JwtContext.createJwt(1L))
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        ArgumentCaptor<Twit> ac = ArgumentCaptor.forClass(Twit.class);
        then(twitWebPort).should().save(eq(1L), ac.capture());
        assertThat(ac.getValue().getContent()).isEqualTo("test writing");
    }

    @Test
    @DisplayName("게시글 수정")
    void update() throws Exception {
        // given
        TwitRequest twitRequest = new TwitRequest("test writing");
        String json = objectMapper.writeValueAsString(twitRequest);

        // when
        mockMvc.perform(put("/twit/{twitId}", "1")
                        .header("Authentication", JwtContext.createJwt(1L))
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        ArgumentCaptor<Twit> ac = ArgumentCaptor.forClass(Twit.class);
        then(twitWebPort).should().update(eq(1L), eq(1L), ac.capture());
        assertThat(ac.getValue().getContent()).isEqualTo("test writing");
    }

    @Test
    @DisplayName("게시글 수정 jwt 토큰 만료 예외")
    void update_jwt_expiration() throws Exception {
        // given
        TwitRequest twitRequest = new TwitRequest("test writing");
        String json = objectMapper.writeValueAsString(twitRequest);
        String jwt = JwtContext.createJwt(1L, 100);
        Thread.sleep(200);

        // expected
        mockMvc.perform(put("/twit/{twitId}", "1")
                        .header("Authentication", jwt)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("권한이 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 수정 jwt 토큰 헤더 없으면 예외")
    void update_non_jwt() throws Exception {
        // given
        TwitRequest twitRequest = new TwitRequest("test writing");
        String json = objectMapper.writeValueAsString(twitRequest);

        // expected
        mockMvc.perform(put("/twit/{twitId}", "1")
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("권한이 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제")
    void delete_twit() throws Exception {
        // when
        mockMvc.perform(delete("/twit/{twitId}", "1")
                        .header("Authentication", JwtContext.createJwt(1L)))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        ArgumentCaptor<Twit> ac = ArgumentCaptor.forClass(Twit.class);
        then(twitWebPort).should().delete(eq(1L), eq(1L));
    }

    @Test
    @DisplayName("게시글 삭제 jwt 토큰 만료 예외")
    void delete_jwt_expiration() throws Exception {
        // given
        String jwt = JwtContext.createJwt(1L, 100);
        Thread.sleep(200);

        // expected
        mockMvc.perform(delete("/twit/{twitId}", "1")
                        .header("Authentication", jwt))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("권한이 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제 jwt 토큰 헤더 없으면 예외")
    void delete_non_jwt() throws Exception {
        // expected
        mockMvc.perform(delete("/twit/{twitId}", "1"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("권한이 없습니다."))
                .andDo(print());
    }
}