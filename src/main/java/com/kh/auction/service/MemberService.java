package com.kh.auction.service;


import com.kh.auction.domain.Member;
import com.kh.auction.repo.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    public List<Member> showAll() {
        return memberDAO.findAll();
    }

    public Member show(String id) {
        return memberDAO.findById(id).orElse(null);
    }

    public Member create(Member member) {
        return memberDAO.save(member);
    }

    public Member duplicate(String id) {
        Member target = memberDAO.findById(id).orElse(null);
        return target;

    }

    public Member update(Member member) {
        Member target = memberDAO.findById(member.getId()).orElse(null);
        if (target != null) {
            return memberDAO.save(member);
        }
        return null;
    }


    public Member delete(String id) {
        Member target = memberDAO.findById(id).orElse(null);
        memberDAO.delete(target);
        return target;
    }

    public Member getByCredentials(String id, String password, PasswordEncoder encoder) {
        Member member = memberDAO.findById(id).orElse(null);
        if (member != null && encoder.matches(password, member.getPassword())) {
            return member;
        }
        return null;
    }
}
