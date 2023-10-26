package com.kh.auction.controller;

import com.kh.auction.domain.AuctionBoard;
import com.kh.auction.domain.Member;
//import com.kh.auction.domain.MemberDTO;
import com.kh.auction.domain.MemberDTO;
import com.kh.auction.security.TokenProvider;
import com.kh.auction.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins={"*"}, maxAge = 6000)
public class MemberController {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberService memberService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//    @GetMapping("/user")
//    public ResponseEntity<List<Member>> showAll() {
//        return ResponseEntity.status(HttpStatus.OK).body(memberService.showAll());
//    }

    @GetMapping("/user")
    public ResponseEntity<Member> show(@AuthenticationPrincipal String id) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.show(id));
    }

    @PostMapping("/public/create")
    public ResponseEntity create(@RequestBody MemberDTO dto) {
        Member vo = new Member();
        Member member = Member.builder()
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .nick(dto.getNick())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .sphone(dto.getSphone())
                .addr(dto.getAddr())
                .build();

        log.info("멤버"+member);
        // 서비스를 이용해 리포지터리에 유저 저장
        Member registerMember = memberService.create(member);
        MemberDTO responseDTO = dto.builder()
                .id(registerMember.getId())
                .name(registerMember.getName())
                .addr(registerMember.getAddr())
                .nick(registerMember.getNick())
                .phone(registerMember.getPhone())
                .sphone(registerMember.getSphone())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


    // 아이디 중복
    @PostMapping("/public/duplicate")
    public ResponseEntity<Map<String, Boolean>> duplicate(@RequestParam(name = "id") String id) {
        try {
            boolean isDuplicate = memberService.duplicate(id) != null;
            log.info(id + (isDuplicate ? " 있는 아이디 입니다" : " 사용 가능한 아이디 입니다"));

            Map<String, Boolean> response = new HashMap<>(); // 혹은 boolean 클래스 만들어서 객체지향성 높이기
            response.put("isDuplicate", isDuplicate);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



    @DeleteMapping("/user/{id}")
    public ResponseEntity<Member> delete(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.delete(id));
    }
    @PostMapping("/public/signin")
    public ResponseEntity authenticate(@RequestBody MemberDTO dto){
        Member member = memberService.getByCredentials(dto.getId(), dto.getPassword(), passwordEncoder);
        log.info(dto.getId()+"ㅂㅁ"+dto.getPassword());
        if(member!=null){
            String token = tokenProvider.create(member);
            MemberDTO responseDTO = MemberDTO.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .addr(member.getAddr())
                    .nick(member.getNick())
                    .phone(member.getPhone())
                    .sphone(member.getSphone())
                    .token(token)
                    .point(member.getPoint())
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        }else {
            return ResponseEntity.badRequest().build();
        }


    }



    // 포인트 api
    @PutMapping("/user/point")
    public ResponseEntity<Member> updatePoint(@AuthenticationPrincipal String id, @RequestBody Member member) {
        int point = member.getPoint();
        Member existMember = memberService.show(id);
        if (existMember != null) {
            existMember.setPoint(existMember.getPoint()+ point);
            Member result = memberService.update(id,existMember.getPoint());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 내 정보 수정 api
    @PutMapping("/user/updateuser")
    public ResponseEntity<Member> updateUser(@AuthenticationPrincipal String id , @RequestBody Member member) {

        String nick = member.getNick();
        String phone = member.getPhone();
        String email = member.getEmail();
        String addr = member.getAddr();
        Member existMember = memberService.show(id);
        existMember.setNick(nick);
        existMember.setPhone(phone);
        existMember.setEmail(email);
        existMember.setAddr(addr);

        Member result = memberService.userUpdate(id, existMember.getNick(), existMember.getPhone(), existMember.getEmail(), existMember.getAddr());
        if(result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}