package com.kh.auction.repo;

import com.kh.auction.domain.AuctionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AuctionItemDetailsDAO extends JpaRepository<AuctionItemDetails, Integer> {
    //등록 번호로 조회
    @Query(value="SELECT * FROM AUCTION_ITEM_DETAILS WHERE AUCTION_NO = :auction_no", nativeQuery = true)
    List<AuctionItemDetails> findByAuctionNo(int auction_no);
}