package com.kh.auction.controller;

import com.kh.auction.domain.AuctionBoard;
import com.kh.auction.domain.Interest;
import com.kh.auction.domain.Member;
import com.kh.auction.service.InterestService;
import com.kh.auction.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctionBoard/*")
@CrossOrigin(origins={"*"}, maxAge = 6000)
public class InterestController {

    @Autowired
    private InterestService interestService;

    @Autowired
    private MemberService memberService;

    // 게시글 관심 등록 : POST - http://localhost:8080/api/user/addList
    @PostMapping("/user/addList")
    public ResponseEntity<Interest> addList(@RequestBody Interest vo, @AuthenticationPrincipal String id, @RequestBody AuctionBoard auctionNo) {
        Member existMember = memberService.show(id);
        vo.setMemberId(existMember);
        vo.setAuctionNo(auctionNo);

        return ResponseEntity.status(HttpStatus.OK).body(interestService.create(vo));
    }

    // 게시글 관심 등록 취소 : DELETE - http://localhost:8080/api/auctionBoard/interest/{id}
    @DeleteMapping("/user/deleteList")
    public ResponseEntity<Interest> delete(@RequestBody int no, @AuthenticationPrincipal String id) {
        Interest vo = new Interest();

        Member existMember = memberService.show(id);

        if(vo.getMemberId().equals(existMember)) {
            if(vo.getInterest_no() == no) {
                return ResponseEntity.status(HttpStatus.OK).body(interestService.delete(vo.getInterest_no()));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }



    // 자신이 관심 등록한 게시글 목록 조회 : GET - http://localhost:8080/api/auctionBoard/{id}/interest
    @GetMapping("/{id}/interest")
    public ResponseEntity<List<Interest>> showInterestList(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(interestService.findByMemberId(id));
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
