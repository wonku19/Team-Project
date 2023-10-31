package com.kh.auction.repo;

import com.kh.auction.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointDAO extends JpaRepository<Point, String> {

}
