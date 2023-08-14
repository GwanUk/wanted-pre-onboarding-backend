package com.example.wantedpreonboardingbackend.mebmer.domain;

import com.example.wantedpreonboardingbackend.common.exception.NotFoundDataException;
import com.example.wantedpreonboardingbackend.common.utils.PasswordEncoderUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBERS")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;
    private String email;
    private String password;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member passwordEncoding() {
        return new Member(email, PasswordEncoderUtil.encode(password));
    }

    public void matchPassword(Member member) {
        boolean result = PasswordEncoderUtil.matches(member.getPassword(), password);
        if (!result) {
            throw new NotFoundDataException();
        }
    }
}
