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

    public AuctionBoard create(AuctionBoard category) {
        return dao.save(category);
    }


    public AuctionBoard update(AuctionBoard auctionBoard) {
        AuctionBoard target = dao.findById(auctionBoard.getAuctionNo()).orElse(null);
        if (target != null) {
            return dao.save(auctionBoard);
        }
        return null;
    }

    public AuctionBoard delete(int no) {
        AuctionBoard category = dao.findById(no).orElse(null);
        dao.delete(category);
        return category;
    }
}
