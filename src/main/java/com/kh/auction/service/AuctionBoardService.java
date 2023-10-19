package com.kh.auction.service;

import com.kh.auction.domain.AuctionBoard;
import com.kh.auction.repo.AuctionBoardDAO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionBoardService {

    @Autowired
    private AuctionBoardDAO auctionBoardDAO;

    public Page<AuctionBoard> showAll(Pageable pageable, BooleanBuilder builder) {
//        return auctionBoardDAO.findAll(builder, pageable);
        return auctionBoardDAO.findAll(builder, pageable);
    }

    public Page<AuctionBoard> Search(String keyword, Pageable pageable){
        return auctionBoardDAO.findByAuctionTitleContaining(keyword, pageable);
    }
    public AuctionBoard show(int no) {
        return auctionBoardDAO.findById(no).orElse(null);
    }
    public Page<AuctionBoard> showAll(Pageable pageable) {
        return auctionBoardDAO.findAll(pageable);
    }
    public AuctionBoard create(AuctionBoard auctionBoard) {
        return auctionBoardDAO.save(auctionBoard);
    }

    public AuctionBoard update(AuctionBoard auctionBoard) {
        AuctionBoard target = auctionBoardDAO.findById(auctionBoard.getAuctionNo()).orElse(null);
        if (target != null) {
            return auctionBoardDAO.save(auctionBoard);
        }
        return null;
    }

    public AuctionBoard delete(int id) {
        AuctionBoard category = auctionBoardDAO.findById(id).orElse(null);
        auctionBoardDAO.delete(category);
        return category;
    }
    public List<AuctionBoard> findByChannelCode(int code) {
        return auctionBoardDAO.findByCategoryNo(code);
    }
}