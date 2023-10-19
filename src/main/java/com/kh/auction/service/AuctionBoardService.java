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


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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


    public Page<AuctionBoard> Search(String keyword, Pageable pageable){
        return auctionBoardDAO.findByAuctionTitleContaining(keyword, pageable);
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

    // Hot 게시글
    public List<AuctionBoard> findByHot(int no) {
        List<AuctionBoard> allAuctionBoards = auctionBoardDAO.findAll();
        Date currentDate = new Date(); // 현재 날짜 가져오기

        // 정렬
        allAuctionBoards.sort(Comparator.comparing(AuctionBoard::getAuctionAttendNo).reversed());

        List<AuctionBoard> filteredAuctionBoards = new ArrayList<>();

        for (AuctionBoard board : allAuctionBoards) {
            Date auctionEndDate = board.getAuctionEndDate();
            // 게시글의 종료 날짜가 현재 날짜 이전이 아닌 경우에만 목록에 추가
            if (auctionEndDate == null || !auctionEndDate.before(currentDate)) {
                filteredAuctionBoards.add(board);
            }

            // 목록이 지정된 개수에 도달하면 루프 종료
            if (filteredAuctionBoards.size() >= no) {
                break;
            }
        }

        return filteredAuctionBoards;
    }

    // New 게시글
    public List<AuctionBoard> findByNew(int no) {
        List<AuctionBoard> allAuctionBoards = auctionBoardDAO.findAll();
        Date currentDate = new Date(); // 현재 날짜 가져오기

        // 정렬
        allAuctionBoards.sort(Comparator.comparing(AuctionBoard::getAuctionDate).reversed());

        List<AuctionBoard> filteredAuctionBoards = new ArrayList<>();

        for (AuctionBoard board : allAuctionBoards) {
            Date auctionEndDate = board.getAuctionEndDate();
            // 게시글의 종료 날짜가 현재 날짜 이전이 아닌 경우에만 목록에 추가
            if (auctionEndDate == null || !auctionEndDate.before(currentDate)) {
                filteredAuctionBoards.add(board);
            }

            // 목록이 지정된 개수에 도달하면 루프 종료
            if (filteredAuctionBoards.size() >= no) {
                break;
            }
        }

        return filteredAuctionBoards;
    }

    public List<AuctionBoard> findByChannelCode(int code) {
        return auctionBoardDAO.findByCategoryNo(code);
    }
}