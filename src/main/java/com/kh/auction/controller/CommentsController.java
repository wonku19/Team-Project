package com.kh.auction.controller;

import com.kh.auction.domain.Comments;
import com.kh.auction.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctionBoard/*")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;


    // 게시글 1개에 따른 댓글 전체 조회 : GET - http://localhost:8080/api/auctionBoard/{id}/comments


    // 댓글 추가 : POST - http://localhost:8080/api/auctionBoard/comments
    @PostMapping("/comments")
    public ResponseEntity<Comments> create(@RequestBody Comments comments) {
        return ResponseEntity.status(HttpStatus.OK).body(commentsService.create(comments));
    }

    // 댓글 수정 : PUT - http://localhost:8080/api/auctionBoard/comments
    @PutMapping("/comments")
    public ResponseEntity<Comments> update(@RequestBody Comments comments) {
        Comments result = commentsService.update(comments);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(commentsService.update(result));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 댓글 삭제 : DELETE - http://localhost:8080/api/auctionBoard/comments/{id}
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Comments> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentsService.delete(id));
    }

    // 댓글 좋아요 추가 : POST - http://localhost:8080/api/auctionBoard/comments/like
//    @PostMapping("/comments/like")
//    public ResponseEntity<CommentsLike> createCommentsLike(@PathVariable CommentsLike like) {
//        return  ResponseEntity.status(HttpStatus.OK).body(likeService.create(like));
//    }

    // 댓글 좋아요 취소 : DELETE - http://localhost:8080/api/auctionBoard/comments/like/{id}
//    @DeleteMapping("/comments/like/{id}")
//    public ResponseEntity<CommentsLike> deleteCommentsLike(@PathVariable String id) {
//        return ResponseEntity.status(HttpStatus.OK).body(likeService.delect(id));
//    }


    @GetMapping("/comments")
    public ResponseEntity<List<Comments>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(commentsService.showAll());
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comments> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentsService.show(id));
    }

}
