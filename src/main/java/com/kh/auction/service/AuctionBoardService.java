package com.kh.auction.service;

import com.kh.auction.domain.AuctionBoard;
import com.kh.auction.domain.Category;
import com.kh.auction.repo.AuctionBoardDAO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class AuctionBoardService {

    @Autowired
    private AuctionBoardDAO auctionBoardDAO;

    public List<AuctionBoard> showAll() {
        return auctionBoardDAO.findAll();
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

    public List<AuctionBoard> findByHot(int no, Sort sort) {
        List<AuctionBoard> allAuctionBoards = auctionBoardDAO.findByHotList(sort);
        if (allAuctionBoards.size() > no) {
            return allAuctionBoards.subList(0, no);
        }
        return allAuctionBoards;
    }

}
