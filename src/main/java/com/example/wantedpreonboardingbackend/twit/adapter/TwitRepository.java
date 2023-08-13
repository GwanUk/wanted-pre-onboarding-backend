package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface TwitRepository extends JpaRepository<Twit, Long> {

    @Query("select t from Twit t left join fetch t.member")
    List<Twit> findAllWithMember();
}
