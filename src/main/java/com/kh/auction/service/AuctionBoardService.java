package com.kh.auction.service;

import com.kh.auction.domain.AuctionBoard;
import com.kh.auction.domain.Category;
import com.kh.auction.repo.AuctionBoardDAO;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class AuctionBoardService {

    @Autowired
    private AuctionBoardDAO auctionBoardDAO;

    public Page<AuctionBoard> showAll(Pageable pageable, BooleanBuilder builder) {
//        return auctionBoardDAO.findAll(builder, pageable);
        return null;
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
    public List<AuctionBoard> findByChannelCode(int code) {
        return auctionBoardDAO.findByCategoryNo(code);
    }


    // Hot 게시글
    public List<AuctionBoard> findByHot(int no) {
        List<AuctionBoard> allAuctionBoards = auctionBoardDAO.findAll();

        // 정렬
        allAuctionBoards.sort(Comparator.comparing(AuctionBoard::getAuctionAttendNo).reversed());

        if (allAuctionBoards.size() > no) {
            return allAuctionBoards.subList(0, no);
        }
        return allAuctionBoards;
    }

    // New 게시글
    public List<AuctionBoard> findByNew(int no) {
        List<AuctionBoard> allAuctionBoards = auctionBoardDAO.findAll();

        // 정렬
        allAuctionBoards.sort(Comparator.comparing(AuctionBoard::getAuctionDate).reversed());

        if (allAuctionBoards.size() > no) {
            return allAuctionBoards.subList(0, no);
        }
        return allAuctionBoards;
    }
}
