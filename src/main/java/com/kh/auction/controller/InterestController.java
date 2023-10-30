package com.kh.auction.controller;

import com.kh.auction.domain.Interest;
import com.kh.auction.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctionBoard/*")
public class InterestController {

    @Autowired
    private InterestService service;


    // 게시글 관심 등록 : POST - http://localhost:8080/api/auctionBoard/interest
    @PostMapping("/interest")
    public ResponseEntity<Interest> create(@PathVariable Interest interest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(interest));
    }


    // 게시글 관심 등록 취소 : DELETE - http://localhost:8080/api/auctionBoard/interest/{id}
    @DeleteMapping("/interest/{id}")
    public ResponseEntity<Interest> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }



    // 자신이 관심 등록한 게시글 목록 조회 : GET - http://localhost:8080/api/auctionBoard/{id}/interest
    @GetMapping("/{id}/interest")
    public ResponseEntity<List<Interest>> showInterestList(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findByMemberId(id));
    }


//    @GetMapping("/interest")
//    public ResponseEntity<List<Interest>> showAll() {
//        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
//    }
//
//    @GetMapping("/interest/{id}")
//    public ResponseEntity<Interest> show(@PathVariable int id) {
//        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
//    }
//
//    @PutMapping("/interest")
//    public ResponseEntity<Interest> update(@PathVariable Interest interest) {
//        Interest result = service.update(interest);
//        if(result != null){
//            return ResponseEntity.status(HttpStatus.OK).body(result);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }

}