package com.kh.auction.service;

import com.kh.auction.domain.Interest;
import com.kh.auction.repo.InterestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestService {

    @Autowired
    private InterestDAO interestDAO;

    // 게시글 관심 등록
    public Interest create(Interest vo) {
        return interestDAO.save(vo);
    }

    // 게시글 관심 등록 취소
    public Interest delete(int no) {
        Interest target = interestDAO.findById(no).orElse(null);
        interestDAO.delete(target);
        return target;
    }

    // 자신이 관심등록한 게시물 목록 조회
    public List<Interest> findByMemberId(String id) {
        return interestDAO.findByMemberId(id);
    }

    public List<Interest> showAll() {
        return interestDAO.findAll();
    }

    public Interest show(int id) {
        return interestDAO.findById(id).orElse(null);
    }

    public Interest update(Interest interest) {
        return interestDAO.save(interest);
    }


}
