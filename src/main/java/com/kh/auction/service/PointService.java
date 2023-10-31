package com.kh.auction.service;

import com.kh.auction.domain.Point;
import com.kh.auction.repo.PointDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PointService {

    @Autowired
    private PointDAO pointDAO;

    public Point show(String id) {
        return pointDAO.findById(id).orElse(null);
    }

}
