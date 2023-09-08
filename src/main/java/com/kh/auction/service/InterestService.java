package com.kh.auction.service;

import com.kh.auction.domain.Interest;
import com.kh.auction.repo.InterestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestService {

    @Autowired
    private InterestDAO dao;

    public List<Interest> showAll() {
        return dao.findAll();
    }

    public Interest show(int id) {
        return dao.findById(id).orElse(null);
    }

    public Interest create(Interest interest) {
        return dao.save(interest);
    }

    public Interest update(Interest interest) {
        return dao.save(interest);
    }

    public Interest delete(int id) {
        Interest data = dao.findById(id).orElse(null);
        dao.delete(data);
        return data;
    }

}
