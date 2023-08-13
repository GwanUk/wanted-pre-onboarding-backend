package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface TwitRepository extends JpaRepository<Twit, Long> {

    @Query("select t from Twit t left join fetch t.member")
    List<Twit> findAllWithMember(Pageable pageable);

    @Query("select t from Twit t left join fetch t.member where t.id = :twitId")
    Optional<Twit> findByIdWithMember(Long twitId);
}
