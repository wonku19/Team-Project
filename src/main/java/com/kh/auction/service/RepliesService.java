package com.kh.auction.service;

import com.kh.auction.domain.Comments;
import com.kh.auction.domain.Replies;
import com.kh.auction.repo.RepliesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepliesService {

    @Autowired
    private RepliesDAO repliesDAO;

    // 대댓글 등록
    public Replies create(Replies replies) {
        return repliesDAO.save(replies);
    }

    // 대댓글 수정
    public Replies update(Replies replies) {
        return repliesDAO.save(replies);
    }

    // 대댓글 삭제
    public Replies delete(int id) {
        Replies data = repliesDAO.findById(id).orElse(null);
        repliesDAO.delete(data);
        return data;
    }

    // 댓글 1개의 대댓글 전체 조회
    public List<Comments> findByRepliesList(int id) {
        return repliesDAO.findByRepliesList(id);
    }

    // 대댓글 좋아요 등록

    // 대댓글 좋아요 등록 취소



    public List<Replies> showAll() {
        return repliesDAO.findAll();
    }

    public Replies show(int id) {
        return repliesDAO.findById(id).orElse(null);
    }


}
