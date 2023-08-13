package com.example.wantedpreonboardingbackend.twit.domain;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TWIT")
public class Twit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TWIT_ID")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public Twit(String content) {
        this.content = content;
    }
}
