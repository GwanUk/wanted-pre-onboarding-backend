package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(MemberPersistenceAdapter.class)
class MemberPersistenceAdapterTest {

    @Autowired
    private MemberPersistenceAdapter memberPersistenceAdapter;

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("회원 가입")
    void save() {
        // given
        Member member = new Member("user@naver.com", "user1234");

        // when
        memberPersistenceAdapter.save(member);

        // then
        List<Member> members = memberPersistenceAdapter.findAll();
        assertThat(members).contains(member);
    }

    @Test
    @Sql("/sql/test-init-data.sql")
    @DisplayName("회원 조회 by email")
    void findByEmail() {
        // when
        Member member = memberPersistenceAdapter.findByEmail("user1@naver.com")
                .orElseThrow(NoSuchElementException::new);

        // then
        assertThat(member.getEmail()).isEqualTo("user1@naver.com");
        assertThat(member.getPassword()).isEqualTo("user1234");
    }
}