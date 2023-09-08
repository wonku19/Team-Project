package com.kh.auction.repo;

import com.kh.auction.domain.AuctionBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionBoardDAO extends JpaRepository<AuctionBoard, Integer> {
}
