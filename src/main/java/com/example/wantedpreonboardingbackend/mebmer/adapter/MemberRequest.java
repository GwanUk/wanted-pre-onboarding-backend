package com.example.wantedpreonboardingbackend.mebmer.adapter;

import com.example.wantedpreonboardingbackend.mebmer.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Getter
@RequiredArgsConstructor
public class MemberRequest {
    @Email(regexp = ".*@.*")
    private final String email;

    @Length(min = 8)
    private final String password;

    public Member toEntity() {
        return new Member(email, password);
    }
}
