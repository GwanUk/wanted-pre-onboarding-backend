package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.mebmer.adapter.MemberRepository;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    @Sql("/sql/twit/twit-data.sql")
    @DisplayName("게시물 전체 조회")
    void find_all_with_member() {
        // when
        PageRequest pageRequest1 = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "id"));
        List<Twit> twits1 = twitPersistenceAdapter.findAllWithMember(pageRequest1);

        PageRequest pageRequest2 = PageRequest.of(1, 3, Sort.by(Sort.Direction.ASC, "id"));
        List<Twit> twits2 = twitPersistenceAdapter.findAllWithMember(pageRequest2);

        // then
        assertThat(twits1).hasSize(3);
        assertThat(twits1.get(0).getId()).isEqualTo(1);
        assertThat(twits1.get(0).getContent()).isEqualTo("user1 writing test a");
        assertThat(twits1.get(1).getId()).isEqualTo(2);
        assertThat(twits1.get(1).getContent()).isEqualTo("user1 writing test b");
        assertThat(twits1.get(2).getId()).isEqualTo(3);
        assertThat(twits1.get(2).getContent()).isEqualTo("user2 writing test c");

        assertThat(twits2).hasSize(3);
        assertThat(twits2.get(0).getId()).isEqualTo(4);
        assertThat(twits2.get(0).getContent()).isEqualTo("user2 writing test d");
        assertThat(twits2.get(1).getId()).isEqualTo(5);
        assertThat(twits2.get(1).getContent()).isEqualTo("user3 writing test e");
        assertThat(twits2.get(2).getId()).isEqualTo(6);
        assertThat(twits2.get(2).getContent()).isEqualTo("user3 writing test f");
    }

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
        List<Twit> twits = twitPersistenceAdapter.findAllWithMember(PageRequest.of(0, 10));
        assertThat(twits).hasSize(1);
        assertThat(twits.get(0).getId()).isEqualTo(1L);
        assertThat(twits.get(0).getContent()).isEqualTo("writing test");
        assertThat(twits.get(0).getMember().getId()).isEqualTo(1L);
        assertThat(twits.get(0).getMember().getEmail()).isEqualTo("user@naver.com");
        assertThat(twits.get(0).getMember().getPassword()).isEqualTo("user1234");
    }
}