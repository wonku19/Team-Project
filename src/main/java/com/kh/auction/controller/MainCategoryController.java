package com.kh.auction.controller;

import com.kh.auction.domain.MainCategory;
import com.kh.auction.service.MainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins={"*"}, maxAge = 6000)
public class MainCategoryController {
    @Autowired
    private MainCategoryService service;

    @GetMapping("/public/MainCategory")
    public ResponseEntity<List<MainCategory>> showAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // .build = body(null) 같음
        }
    }
    @PostMapping("/public/MainCategory")
    public ResponseEntity<MainCategory> create(@RequestBody MainCategory category) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.create(category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}
