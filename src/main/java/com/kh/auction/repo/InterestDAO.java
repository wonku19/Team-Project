package com.kh.auction.repo;

import com.kh.auction.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestDAO extends JpaRepository<Interest, Integer> {
}
