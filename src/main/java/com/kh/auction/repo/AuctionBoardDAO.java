package com.kh.auction.repo;

import com.kh.auction.domain.AuctionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AuctionBoardDAO extends JpaRepository<AuctionBoard, Integer> {

    //특정 경매 제목에 대한 경매 목록을 검색
    List<AuctionBoard> findByAuctionTitleContaining(String auctionTitle);

    @Query("SELECT a FROM AuctionBoard a WHERE a.auctionDate BETWEEN :startDate AND :endDate")
    List<AuctionBoard> findAuctionsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
