package com.kh.auction.repo;

import com.kh.auction.domain.Category2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Category2DAO extends JpaRepository<Category2, Integer> {
    @Query(value="SELECT * FROM auction_board WHERE auction_no:auction_no",nativeQuery = true)
    List<Category2> findByAuctionNo2 (int auction_no);

}