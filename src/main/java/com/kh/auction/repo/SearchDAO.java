package com.kh.auction.repo;

import com.kh.auction.domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchDAO extends JpaRepository<Search, Integer> {
}
