package com.kh.auction.service;

import com.kh.auction.domain.MainCategory;
import com.kh.auction.repo.MainCategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainCategoryService {
    @Autowired
    private MainCategoryDAO dao;

    public List<MainCategory> showAll(){
        return dao.findAll();
    }

    public MainCategory show(int code){
        return dao.findById(code).orElse(null);
    }

    public MainCategory create(MainCategory category){
        return dao.save(category);
    }

    public MainCategory update(MainCategory category) {
        return dao.save(category);
    }

    public MainCategory delete(int code) {
        MainCategory data = dao.findById(code).orElse(null);
        dao.delete(data);
        return data;
    }


}
