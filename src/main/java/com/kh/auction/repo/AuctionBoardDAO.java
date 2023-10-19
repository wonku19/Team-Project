package com.kh.auction.repo;

import com.kh.auction.domain.AuctionBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionBoardDAO extends JpaRepository<AuctionBoard, Integer>, QuerydslPredicateExecutor<AuctionBoard> {

    @Query(value="SELECT * FROM auction_board WHERE category_No:category_No", nativeQuery = true)
    List<AuctionBoard> findByCategoryNo(int category_No);

    @Query("SELECT a FROM AuctionBoard a ORDER BY a.auctionAttendNo DESC")
    List<AuctionBoard> findAllOrderByAuctionAttendNoDesc();

    @Query("SELECT ab FROM AuctionBoard ab WHERE ab.auctionNo = :auction_no")
    AuctionBoard show(@Param("auction_no") int auction_no);

    Page<AuctionBoard> findByAuctionTitleContaining(String keyword, Pageable pageable);
}







