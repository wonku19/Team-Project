package com.kh.auction.repo;

import com.kh.auction.domain.AuctionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface AuctionBoardDAO extends JpaRepository<AuctionBoard, Integer>, QuerydslPredicateExecutor<AuctionBoard> {

}
