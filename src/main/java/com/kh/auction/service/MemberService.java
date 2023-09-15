package com.kh.auction.service;


import com.kh.auction.domain.Member;
import com.kh.auction.repo.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MemberService {

    @Autowired
    private MemberDAO memberDAO;

    public List<Member> showAll() {
        return memberDAO.findAll();
    }

    public Member show(int no) {
        return memberDAO.findById(no).orElse(null);
    }

    public Member create(Member member) {
        return memberDAO.save(member);
    }


    public Member update(Member member) {
        Member target = memberDAO.findById(member.getNo()).orElse(null);
        if (target != null) {
            return memberDAO.save(member);
        }
        return null;
    }


    public Member delete(int no) {
        Member target = memberDAO.findById(no).orElse(null);
        memberDAO.delete(target);
        return target;
    }

}
