package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.mebmer.adapter.MemberRepository;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TwitPersistenceAdapter.class)
class TwitPersistenceAdapterTest {

    @Autowired
    private TwitPersistenceAdapter twitPersistenceAdapter;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Sql("/sql/twit/twit-table.sql")
    @DisplayName("게시물 등록")
    void save() {
        // given
        Member member = memberRepository.findAll().get(0);
        Twit twit = new Twit("writing test");
        twit.setMember(member);

        // when
        twitPersistenceAdapter.save(twit);

        // then
        List<Twit> twits = twitPersistenceAdapter.findAllWithMember();
        assertThat(twits).hasSize(1);
        assertThat(twits.get(0).getId()).isEqualTo(1L);
        assertThat(twits.get(0).getContent()).isEqualTo("writing test");
        assertThat(twits.get(0).getMember().getId()).isEqualTo(1L);
        assertThat(twits.get(0).getMember().getEmail()).isEqualTo("user@naver.com");
        assertThat(twits.get(0).getMember().getPassword()).isEqualTo("user1234");
    }
}