package com.kh.auction.controller;

import com.kh.auction.domain.Comments;
import com.kh.auction.domain.CommentsDTO;
import com.kh.auction.domain.Member;
import com.kh.auction.service.CommentsService;
import jakarta.persistence.Column;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins={"*"}, maxAge = 6000)
@Slf4j
public class CommentsController {

    @Autowired
    private CommentsService service;

    @Column(name="comment_desc")
    private String commentDesc;

    @Column(name="comment_date")
    private Date commentDate;

    @Column(name="auction_No")
    private int auctioNo;

    @Autowired
    private CommentsService comment;


    // 게시글 1개에 따른 댓글 전체 조회 : GET - http://localhost:8080/api/auctionBoard/{id}/comments


    // 댓글 추가 : POST - http://localhost:8080/api/auctionBoard/comments
    @PostMapping("/comments")
    public ResponseEntity<Comments> create(@RequestBody Comments comments) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(comments));
    }

    // 댓글 수정 : PUT - http://localhost:8080/api/auctionBoard/comments
    @PutMapping("/comments")
    public ResponseEntity<Comments> update(@RequestBody Comments comments) {
        Comments result = service.update(comments);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(result));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 댓글 삭제 : DELETE - http://localhost:8080/api/auctionBoard/comments/{id}
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Comments> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
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
        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Comments> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    @GetMapping("/public/{id}/comment")
    public ResponseEntity<List<CommentsDTO>> videoCommentList(@PathVariable int id) {
        List<Comments> topList = comment.getAllTopLevelComments(id);
        log.info("top : " + topList);

        List<CommentsDTO> response = new ArrayList<>();

        for(Comments item : topList) {
            CommentsDTO dto = new CommentsDTO();
            dto.setAuctionNo(item.getAuctionNo());
            dto.setCommentNo(item.getCommentNo());
            dto.setContent(item.getContent());
            dto.setMember(item.getMemberId());
            List<Comments> result = comment.getRepliesByCommentId(item.getCommentNo(), id);
            dto.setReplies(result);
            response.add(dto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/user/comment/{no}")
    public ResponseEntity<Comments> createComment(@RequestBody Comments vo, @AuthenticationPrincipal String id, @PathVariable int no) {
        Member member = new Member();
        member.setId(id);
        vo.setMemberId(member);
        vo.setAuctionNo(no);
        return ResponseEntity.status(HttpStatus.OK).body(comment.create(vo));
    }

}
