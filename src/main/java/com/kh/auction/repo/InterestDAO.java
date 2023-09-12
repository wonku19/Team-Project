package com.kh.auction.repo;

import com.kh.auction.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterestDAO extends JpaRepository<Interest, Integer> {

    // 자신이 관심 등록한 게시물 목록 조회 - SELECT * FROM interest WHERE id=?
    @Query(value = "SELECT * FROM interest WHERE id = :id", nativeQuery = true)
    List<Interest> findByMemberId(String id);
}
