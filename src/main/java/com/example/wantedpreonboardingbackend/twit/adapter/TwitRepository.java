package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface TwitRepository extends JpaRepository<Twit, Long> {

    @Query(value = "select t from Twit t left join fetch t.member",
            countQuery = "select count(t) from Twit t")
    Page<Twit> findAllWithMember(Pageable pageable);

    @Query("select t from Twit t left join fetch t.member where t.id = :twitId")
    Optional<Twit> findByIdWithMember(Long twitId);
}
