package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.common.utils.JwtContext;
import com.example.wantedpreonboardingbackend.twit.application.TwitWebPort;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        // when
        mockMvc.perform(get("/twit")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        then(twitWebPort).should().findAllWithMember(pageRequest);
    }

    @Test
    @DisplayName("게시글 조회 by id")
    void find_by_id() throws Exception {
        // when
        mockMvc.perform(get("/twit/{twitId}", "1")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        then(twitWebPort).should().findById(ArgumentMatchers.eq(1L));
    }

    @Test
    @DisplayName("게시글 생성")
    void save() throws Exception {
        // given
        Twit twit = new Twit("test writing");
        String json = objectMapper.writeValueAsString(twit);

        // when
        mockMvc.perform(post("/twit")
                        .header("Authentication", JwtContext.createJwt(1L))
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        ArgumentCaptor<Twit> ac = ArgumentCaptor.forClass(Twit.class);
        then(twitWebPort).should().save(ArgumentMatchers.eq(1L), ac.capture());
        assertThat(ac.getValue().getContent()).isEqualTo("test writing");
    }

}