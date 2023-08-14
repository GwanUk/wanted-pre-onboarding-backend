package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.common.exception.NotFoundDataException;
import com.example.wantedpreonboardingbackend.mebmer.adapter.MemberRepository;
import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Sql("/sql/test-init-data.sql")
    @DisplayName("게시물 전체 조회")
    void find_all_with_member() {
        // when
        PageRequest pageRequest1 = PageRequest.of(0, 3);
        Page<Twit> twits1 = twitPersistenceAdapter.findAllWithMember(pageRequest1);

        PageRequest pageRequest2 = PageRequest.of(1, 3);
        Page<Twit> twits2 = twitPersistenceAdapter.findAllWithMember(pageRequest2);

        // then
        List<Twit> content1 = twits1.getContent();
        assertThat(content1).hasSize(3);
        assertThat(content1.get(0).getId()).isEqualTo(1L);
        assertThat(content1.get(0).getContent()).isEqualTo("user1 writing test a");
        assertThat(content1.get(1).getId()).isEqualTo(2L);
        assertThat(content1.get(1).getContent()).isEqualTo("user1 writing test b");
        assertThat(content1.get(2).getId()).isEqualTo(3L);
        assertThat(content1.get(2).getContent()).isEqualTo("user2 writing test c");

        List<Twit> content2 = twits2.getContent();
        assertThat(content2).hasSize(3);
        assertThat(content2.get(0).getId()).isEqualTo(4L);
        assertThat(content2.get(0).getContent()).isEqualTo("user2 writing test d");
        assertThat(content2.get(1).getId()).isEqualTo(5L);
        assertThat(content2.get(1).getContent()).isEqualTo("user3 writing test e");
        assertThat(content2.get(2).getId()).isEqualTo(6L);
        assertThat(content2.get(2).getContent()).isEqualTo("user3 writing test f");
    }

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("게시물 단건 조회")
    void find_member_by_id() {
        // when
        Twit twit = twitPersistenceAdapter.findByIdWithMember(1L).orElseThrow(NotFoundDataException::new);

        // then
        assertThat(twit.getId()).isEqualTo(1L);
        assertThat(twit.getContent()).isEqualTo("user1 writing test a");
        assertThat(twit.getMember().getId()).isEqualTo(1L);
        assertThat(twit.getMember().getEmail()).isEqualTo("user1@naver.com");
        assertThat(twit.getMember().getPassword()).isEqualTo("user1234");
    }

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("게시물 등록")
    void save() {
        // given
        Member member = memberRepository.findAll().get(0);
        Twit twit = new Twit("writing test");
        twit.setMember(member);

        // when
        twitPersistenceAdapter.save(twit);

        // then
        Page<Twit> twits = twitPersistenceAdapter.findAllWithMember(PageRequest.of(0, 10));
        List<Twit> content = twits.getContent();
        assertThat(content).hasSize(7);
        assertThat(content.get(6).getId()).isEqualTo(7L);
        assertThat(content.get(6).getContent()).isEqualTo("writing test");
        assertThat(content.get(6).getMember().getId()).isEqualTo(1L);
        assertThat(content.get(6).getMember().getEmail()).isEqualTo("user1@naver.com");
        assertThat(content.get(6).getMember().getPassword()).isEqualTo("user1234");
    }

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("게시물 삭제")
    void delete() {
        // when
        Twit twit = twitPersistenceAdapter.findByIdWithMember(1L).orElseThrow(NotFoundDataException::new);
        twitPersistenceAdapter.delete(twit);

        // then
        List<Long> ids = twitPersistenceAdapter
                .findAllWithMember(PageRequest.of(0, 10))
                .stream()
                .map(Twit::getId)
                .toList();
        assertThat(ids).doesNotContain(1L);
    }
}