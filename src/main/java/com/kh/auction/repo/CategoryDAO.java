package com.kh.auction.repo;

import com.kh.auction.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

    //번호에 따른 조회

    @Query(value="SELECT * FROM AUCTION_ITEM_DETAILS WHERE AUCTION_NO = :auction_no", nativeQuery = true)
    List<AuctionItemDetails> findByAuctionNo(int auction_no);
}
