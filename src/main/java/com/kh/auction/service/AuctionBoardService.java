package com.kh.auction.service;

import com.kh.auction.domain.AuctionBoard;
import com.kh.auction.repo.AuctionBoardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionBoardService {

    @Autowired
    private AuctionBoardDAO dao;

    public List<AuctionBoard> showAll() {
        return dao.findAll();
    }

    public AuctionBoard show(int no) {
        return dao.findById(no).orElse(null);
    }

    public AuctionBoard create(AuctionBoard auctionBoard) {
        return dao.save(auctionBoard);
    }


    public AuctionBoard update(AuctionBoard auctionBoard) {
        AuctionBoard target = dao.findById(auctionBoard.getAuctionNo()).orElse(null);
        if (target != null) {
            return dao.save(auctionBoard);
        }
        return null;
    }

    public AuctionBoard delete(int id) {
        AuctionBoard category = dao.findById(id).orElse(null);
        dao.delete(category);
        return category;
    }
}
