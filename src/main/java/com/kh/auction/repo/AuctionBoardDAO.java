package com.kh.auction.repo;

import com.kh.auction.domain.AuctionBoard;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionBoardDAO extends JpaRepository<AuctionBoard, Integer> {

    @Query(value = "SELECT * FROM auction_board", nativeQuery = true)
    List<AuctionBoard> findByHotList();

}
