package com.kh.auction.service;

import com.kh.auction.domain.Search;
import com.kh.auction.repo.SearchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchDAO dao;

    public List<Search> showAll() {
        return dao.findAll();
    }

    public Search show(int searchSeq) {
        return dao.findById(searchSeq).orElse(null);
    }

    public Search create(Search search) {
        return dao.save(search);
    }

    public Search update(Search search) {
        Search target = dao.findById(search.getSearchSeq()).orElse(null);
        if (target != null) {
            return dao.save(search);
        }
        return null;
    }

    public Search delete(int searchSeq) {
        Search target = dao.findById(searchSeq).orElse(null);
        if(target != null) {
            dao.delete(target);
            return target;
        }
        return null;
    }
}