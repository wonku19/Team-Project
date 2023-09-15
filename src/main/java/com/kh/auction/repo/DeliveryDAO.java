package com.kh.auction.repo;

import com.kh.auction.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryDAO extends JpaRepository<Delivery, Integer> {

    // 등록 번호로 조회
    @Query(value="SELECT * FROM DELIVERY WHERE AUCTION_NO = :auction_no", nativeQuery = true)
    List<DELIVERY> findByAuctionNo(int auction_no);

     // 멤버 아이디로 조회
    @Query(value="SELECT * FROM DELIVERY WHERE MEMBER_ID = :member_id", nativeQuery = true)
    List<Delivery> findByMemberId(String member_id);
}

