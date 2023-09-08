package com.kh.auction.service;


import com.kh.auction.domain.Member;
import com.kh.auction.repo.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MemberService {

    @Autowired
    private MemberDAO dao;

    public List<Member> showAll() {
        return dao.findAll();
    }

    public Member show(int no) {
        return dao.findById(no).orElse(null);
    }

    public Member create(Member member) {
        return dao.save(member);
    }


    public Member update(Member member) {
        Member target = dao.findById(member.getNo()).orElse(null);
        if (target != null) {
            return dao.save(member);
        }
        return null;
    }


    public Member delete(int no) {
        Member target = dao.findById(no).orElse(null);
        dao.delete(target);
        return target;
    }

}
