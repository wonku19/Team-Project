package com.kh.auction.controller;

import com.kh.auction.domain.AuctionBoard;
import com.kh.auction.service.AuctionBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class AuctionBoardController {

    @Autowired
    private AuctionBoardService service;

    @GetMapping("/auction")
    public ResponseEntity<List<AuctionBoard>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
    }

    @GetMapping("/auction/{no}")
    public ResponseEntity<AuctionBoard> show(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(no));
    }

    @PostMapping("/auction")
    public ResponseEntity<AuctionBoard> create(@RequestBody AuctionBoard auctionBoard) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(auctionBoard));
    }

    @PutMapping("/auction")
    public ResponseEntity<AuctionBoard> update(@RequestBody AuctionBoard auctionBoard) {
        AuctionBoard result = service.update(auctionBoard);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/auction/{no}")
    public ResponseEntity<AuctionBoard> delete(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(no));
    }
}
