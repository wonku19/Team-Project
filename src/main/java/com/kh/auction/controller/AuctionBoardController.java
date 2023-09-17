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
    private AuctionBoardService auctionBoardService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/auction")
    public ResponseEntity<List<AuctionBoard>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(auctionBoardService.showAll());
    }

    @GetMapping("/auction/{no}")
    public ResponseEntity<AuctionBoard> show(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(auctionBoardService.show(no));
    }

    @PostMapping("/auction")
    public ResponseEntity<AuctionBoard> create(@RequestBody AuctionBoard auctionBoard) {
        return ResponseEntity.status(HttpStatus.OK).body(auctionBoardService.create(auctionBoard));
    }

    @PutMapping("/auction")
    public ResponseEntity<AuctionBoard> update(@RequestBody AuctionBoard auctionBoard) {
        AuctionBoard result = auctionBoardService.update(auctionBoard);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/auction/{no}")
    public ResponseEntity<AuctionBoard> delete(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(auctionBoardService.delete(no));
    }
    // 카테고리
    @GetMapping("/auction/{auctionNo}/category")
    public ResponseEntity<List<Category>> categoryList(@PathVariable int auctionNo){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findByAuctionNo(auctionNo));
    }

    // 특정 경매 게시글에 달린 댓글들 조회
    @GetMapping("/auction/{no}/comments")
    public ResponseEntity<List<Comments>> showComments(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(commentsService.boardCommentsList(no));
    }

}
