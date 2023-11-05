package com.kh.auction.service;

import com.kh.auction.domain.Category;
import com.kh.auction.domain.Category2;
import com.kh.auction.repo.Category2DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService2 {


    @Autowired
    private Category2DAO category2DAO;

//    @Autowired(required = true)
//    private JPAQueryFactory queryFactory;

//    private final QVideoComment qVideoComment = QVideoComment.videoComment;

    public List<Category2> findByAuctionNo(int auctionNo){
        return category2DAO.findByAuctionNo2(auctionNo);
    }

    public List<Category2> showAll() {
        return category2DAO.findAll();
    }

    public Category2 show(int no) {
        return category2DAO.findById(no).orElse(null);
    }

    public Category2 create(Category2 category) {
        return category2DAO.save(category);
    }


    public Category2 update(Category2 category2) {
        Category2 target = category2DAO.findById(category2.getCategoryNo()).orElse(null);
        if (target != null) {
            return category2DAO.save(category2);
        }
        return null;
    }

    public Category2 delete(int no) {
        Category2 category2 = category2DAO.findById(no).orElse(null);
        category2DAO.delete(category2);
        return category2;
    }




}