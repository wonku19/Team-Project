package com.kh.auction.controller;

import com.kh.auction.domain.Comments;
import com.kh.auction.domain.Replies;
import com.kh.auction.service.RepliesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctionBoard/*")
public class RepliesController {

    @Autowired
    private RepliesService service;

    // 대댓글 등록 : POST - http://localhost:8080/api/auctionBoard/comments/replies
    @PostMapping("/comments/replies")
    public ResponseEntity<Replies> create(@PathVariable Replies replies) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(replies));
    }

    // 대댓글 수정 : PUT - http://localhost:8080/api/auctionBoard/comments/replies
    @PutMapping("/comments/replies")
    public ResponseEntity<Replies> update(@PathVariable Replies replies) {
        Replies result = service.update(replies);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 대댓글 삭제 : DELETE - http://localhost:8080/api/auctionBoard/comments/replies/{id}
    @DeleteMapping("/comments/replies/{id}")
    public ResponseEntity<Replies> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

    // 댓글 1개에 따른 대댓글 전체 조회 : POST - http://localhost:8080/api/auctionBoard/comments/{id}/replies
    @GetMapping("/comments/{id}/replies")
    public ResponseEntity<List<Comments>> showRepliesList(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findByRepliesList(id));
    }

    // 대댓글 좋아요 등록 : POST - http://localhost:8080/api/auctionBoard/comments/replies/like
//    @PostMapping("/comments/replies/like")
//    public ResponseEntity<RepliesLike> createRepliesLike(){
//        return ResponseEntity.status(HttpStatus.OK).body(repliesLikeDAO.crate());
//    }

    // 대댓글 좋아요 등록 취소 : DELETE - http://localhost:8080/api/auctionBoard/comments/replies/like/{id}
//    @DeleteMapping("/comments/replies/like/{id}")
//    public ResponseEntity<RepliesLike> deleteRepliesLike() {
//        return ResponseEntity.status(HttpStatus.OK).body(repliesLikeDAO.delete());
//    }



    @GetMapping("/comments/replies")
    public ResponseEntity<List<Replies>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
    }

    @GetMapping("/comments/replies/{id}")
    public ResponseEntity<Replies> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    
}
