package com.kh.auction.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@DynamicInsert
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @Column(name = "member_id")
    private String id;
    @Column(name = "member_pwd")
    private String password;
    @Column(name = "member_nick")
    private String nick;
    @Column(name = "member_name")
    private String name;
    @Column(name = "member_gender")
    private char gender;
    @Column(name = "member_phone")
    private String phone; // 회원 전화번호
    @Column(name = "member_sphone")
    private String sphone; // 회원 안심번호
    @Column(name = "member_email")
    private String email;
    @Column(name = "member_addr")
    private String addr;
    @Column(name = "member_signup_date")
    private Date singupDate;
    @Column(name = "member_authority")
    private int authority;

}
