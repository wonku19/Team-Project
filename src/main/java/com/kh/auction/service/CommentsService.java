package com.kh.auction.service;

import com.kh.auction.domain.Comments;
import com.kh.auction.repo.CommentsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {

    @Autowired
    private CommentsDAO commentsDAO;

    // 게시글 1개에 따른 댓글 전체 조회
    public List<Comments> boardCommentsList(int no) {
        Comments target = commentsDAO.findById(no).orElse(null);
        if (target != null) {
            return commentsDAO.findByBoardComments(no);
        }
        return null;
    }


    public List<Comments> showAll() {
        return commentsDAO.findAll();
    }

    public Comments show(int id) {
        return commentsDAO.findById(id).orElse(null);
    }

    public Comments create(Comments comments) {
        return commentsDAO.save(comments);
    }

    public Comments update(Comments comments) {
        return commentsDAO.save(comments);
    }

    public Comments delete(int id) {
        Comments data = commentsDAO.findById(id).orElse(null);
        commentsDAO.delete(data);
        return data;
    }


}
