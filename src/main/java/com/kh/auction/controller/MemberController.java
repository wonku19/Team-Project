package com.kh.auction.controller;

import com.kh.auction.domain.Member;
import com.kh.auction.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/user")
    public ResponseEntity<List<Member>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.showAll());
    }

    @GetMapping("/user/{no}")
    public ResponseEntity<Member> show(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.show(no));
    }

    @PostMapping("/user")
    public ResponseEntity<Member> create(@RequestBody Member member) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.create(member));
    }

    @PutMapping("/user")
    public ResponseEntity<Member> update(@RequestBody Member member) {
        Member result = memberService.update(member);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @DeleteMapping("/user/{no}")
    public ResponseEntity<Member> delete(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.delete(no));
    }

    // 멤버 아이디로 배송정보 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Delivery>> getDeliveryByMemberId(@PathVariable String memberId) {
        List<Delivery> deliveries = deliveryService.getDeliveryByMemberId(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(deliveries);
    }

}
