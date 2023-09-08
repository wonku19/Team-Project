package com.kh.auction.service;

import com.kh.auction.domain.Category;
import com.kh.auction.repo.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDAO dao;

    public List<Category> showAll() {
        return dao.findAll();
    }

    public Category show(int no) {
        return dao.findById(no).orElse(null);
    }

    public Category create(Category category) {
        return dao.save(category);
    }


    public Category update(Category category) {
        Category target = dao.findById(category.getCategoryNo()).orElse(null);
        if (target != null) {
            return dao.save(category);
        }
        return null;
    }

    public Category delete(int no) {
        Category category = dao.findById(no).orElse(null);
        dao.delete(category);
        return category;
    }


}
