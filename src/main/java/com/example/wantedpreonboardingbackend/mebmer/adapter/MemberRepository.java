package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

interface MemberRepository extends JpaRepository<Member, Long> {
}
