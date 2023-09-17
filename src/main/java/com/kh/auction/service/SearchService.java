package com.kh.auction.service;

import com.kh.auction.domain.Search;
import com.kh.auction.repo.SearchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchDAO searchDAO;

    public List<Search> showAll() {
        return searchDAO.findAll();
    }

    public Search show(int searchSeq) {
        return searchDAO.findById(searchSeq).orElse(null);
    }

    public Search create(Search search) {
        return searchDAO.save(search);
    }

    public Search update(Search search) {
        Search target = searchDAO.findById(search.getSearchSeq()).orElse(null);
        if (target != null) {
            return searchDAO.save(search);
        }
        return null;
    }

    public Search delete(int searchSeq) {
        Search target = searchDAO.findById(searchSeq).orElse(null);
        if(target != null) {
            searchDAO.delete(target);
            return target;
        }
        return null;
    }
}