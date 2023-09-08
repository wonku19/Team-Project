package com.kh.auction.controller;

import com.kh.auction.domain.Interest;
import com.kh.auction.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class InterestController {

    @Autowired
    private InterestService service;

    @GetMapping("/interest")
    public ResponseEntity<List<Interest>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
    }


    @GetMapping("/interest/{id}")
    public ResponseEntity<Interest> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    @PostMapping("/interest")
    public ResponseEntity<Interest> create(@PathVariable Interest interest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(interest));
    }

    @PutMapping("/interest")
    public ResponseEntity<Interest> update(@PathVariable Interest interest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(interest));
    }

    @DeleteMapping("/interest/{id}")
    public ResponseEntity<Interest> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

}
