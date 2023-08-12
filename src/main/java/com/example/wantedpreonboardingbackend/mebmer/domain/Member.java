package com.example.wantedpreonboardingbackend.mebmer.domain;

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
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Email(regexp = ".*@.*")
    private String email;

    @Length(min = 8)
    private String password;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void passwordEncoding() {
        password = PasswordEncoderUtil.encode(password);
    }

    public boolean matchPassword(Member member) {
        return PasswordEncoderUtil.matches(member.getPassword(), password);
    }
}
