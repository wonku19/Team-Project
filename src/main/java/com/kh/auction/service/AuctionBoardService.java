package com.kh.auction.service;

import com.kh.auction.domain.AuctionBoard;
import com.kh.auction.repo.AuctionBoardDAO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionBoardService {

    @Autowired
    private AuctionBoardDAO auctionBoardDAO;

    public Page<AuctionBoard> showAll(Pageable pageable, BooleanBuilder builder) {
        return auctionBoardDAO.findAll(builder, pageable);
    }


    public AuctionBoard show(int no) {
        return auctionBoardDAO.findById(no).orElse(null);
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
    public List<AuctionBoard> findByCategoryNo(int code) {
        return auctionBoardDAO.findByCategoryNo(code);
    }
    public List<AuctionBoard> getAuctionBoardsOrderByAttendNoDesc() {
        return auctionBoardDAO.findAllOrderByAuctionAttendNoDesc();
    }

}
