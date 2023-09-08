package com.kh.auction.repo;

import com.kh.auction.domain.AuctionItemDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsDAO extends JpaRepository<AuctionItemDetails, Integer> {

}
