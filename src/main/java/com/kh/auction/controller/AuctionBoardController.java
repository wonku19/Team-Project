package com.kh.auction.controller;

import com.kh.auction.domain.AuctionBoard;
import com.kh.auction.domain.Category;
import com.kh.auction.domain.Comments;
import com.kh.auction.service.AuctionBoardService;
import com.kh.auction.service.CategoryService;
import com.kh.auction.service.CommentsService;
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

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private CategoryService category;

    @GetMapping("/auction")
    public ResponseEntity<List<AuctionBoard>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
    }

    @GetMapping("/auction/{no}")
    public ResponseEntity<AuctionBoard> show(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(no));
    }

    @PostMapping("/user/auction")
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
    // 카테고리
    @GetMapping("/auction/{auctionNo}/category")
    public ResponseEntity<List<Category>> categoryList(@PathVariable int auctionNo){
        return ResponseEntity.status(HttpStatus.OK).body(category.findByAuctionNo(auctionNo));
    }

    // 특정 경매 게시글에 달린 댓글들 조회
    @GetMapping("/auction/{no}/comments")
    public ResponseEntity<List<Comments>> showComments(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(commentsService.boardCommentsList(no));
    }
    // 경매 번호로 아이템 상세정보 조회
    @GetMapping("/auction/itemDetails/{itemNo}")
        public ResponseEntity<AuctionItemDetails> getItemDetailsByItemNo(@PathVariable int itemNo) {
            AuctionItemDetails itemDetails = auctionItemDetailsService.getAuctionItemDetailsByItemNo(itemNo);
            try{ 
                 return ResponseEntity.status(HttpStatus.OK).body(itemDetails);
            } ecatch (Exception e){
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
    // 경매 번호로 배송 정보 조회
    @GetMapping("/auction/{auctionNo}")
    public ResponseEntity<List<Delivery>> getDeliveryByAuctionNo(@PathVariable int auctionNo) {
        List<Delivery> deliveries = deliveryService.getDeliveryByAuctionNo(auctionNo);
        return ResponseEntity.status(HttpStatus.OK).body(deliveries);
    }
}
