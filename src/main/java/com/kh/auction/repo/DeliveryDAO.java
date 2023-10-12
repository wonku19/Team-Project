package com.kh.auction.repo;

import com.kh.auction.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryDAO extends JpaRepository<Delivery, Integer> {

}
