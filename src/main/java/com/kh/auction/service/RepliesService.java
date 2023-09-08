package com.kh.auction.service;

import com.kh.auction.domain.Replies;
import com.kh.auction.repo.RepliesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepliesService {

    @Autowired
    private RepliesDAO dao;

    public List<Replies> showAll() {
        return dao.findAll();
    }

    public Replies show(int id) {
        return dao.findById(id).orElse(null);
    }

    public Replies create(Replies replies) {
        return dao.save(replies);
    }

    public Replies update(Replies replies) {
        return dao.save(replies);
    }

    public Replies delete(int id) {
        Replies data = dao.findById(id).orElse(null);
        dao.delete(data);
        return data;
    }

}
