package com.example.wantedpreonboardingbackend.twit.adapter;

import com.example.wantedpreonboardingbackend.twit.domain.Twit;
import org.springframework.data.jpa.repository.JpaRepository;

interface TwitRepository extends JpaRepository<Twit, Long> {
}
