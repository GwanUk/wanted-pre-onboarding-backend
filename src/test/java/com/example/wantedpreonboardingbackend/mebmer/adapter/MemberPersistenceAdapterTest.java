package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(MemberPersistenceAdapter.class)
class MemberPersistenceAdapterTest {

    @Autowired
    private MemberPersistenceAdapter memberPersistenceAdapter;

    @Test
    @Sql("/sql/member/member-data.sql")
    @DisplayName("회원 가입")
    void save() {
        // given
        Member member = new Member("user@naver.com", "user1234");

        // when
        memberPersistenceAdapter.save(member);

        // then
        List<Member> members = memberPersistenceAdapter.findAll();
        assertThat(members.get(0)).isEqualTo(member);
    }
}