package com.kh.auction.controller;

import com.kh.auction.domain.AuctionItemDetails;
import com.kh.auction.service.AuctionItemDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class AuctionItemDetailsController {

    @Autowired
    private AuctionItemDetailsService auctionItemDetailsService;

    @GetMapping("/auctionItemDetails")
    public ResponseEntity<List<AuctionItemDetails>> showAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(auctionItemDetailsService.showAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/auctionItemDetails/{id}")
    public ResponseEntity<AuctionItemDetails> show(@PathVariable int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(auctionItemDetailsService.show(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/auctionItemDetails")
    public ResponseEntity<AuctionItemDetails> create(@RequestBody AuctionItemDetails auctionItemDetails) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(auctionItemDetailsService.create(auctionItemDetails));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


    }
    @PutMapping("/auctionItemDetails")
    public ResponseEntity<AuctionItemDetails> update(@RequestBody AuctionItemDetails auctionItemDetails){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(auctionItemDetailsService.update(auctionItemDetails));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/auctionItemDetails/{id}")
    public ResponseEntity<AuctionItemDetails> delete(@PathVariable int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(auctionItemDetailsService.delete(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
