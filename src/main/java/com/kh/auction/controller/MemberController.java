package com.kh.auction.controller;

import com.kh.auction.domain.Member;
//import com.kh.auction.domain.MemberDTO;
import com.kh.auction.domain.MemberDTO;
import com.kh.auction.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//    @Autowired
//    private TokenProvider tokenProvider;

    @Autowired
    private MemberService memberService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @GetMapping("/user")
    public ResponseEntity<List<Member>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.showAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Member> show(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.show(id));
    }

    @PostMapping("/user/create")
    public ResponseEntity create(@RequestBody MemberDTO dto) {
        Member vo = new Member();
        Member member = Member.builder()
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .nick(dto.getNick())
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
    @PostMapping("/user/duplicate")
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
    @PostMapping("/user/signin")
    public ResponseEntity authenticate(@RequestBody MemberDTO dto){
        Member member = memberService.getByCredentials(dto.getId(), dto.getPassword(), passwordEncoder);
        if(member!=null){
//            String token = tokenProvider.create(member);
            MemberDTO responseDTO = MemberDTO.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .addr(member.getAddr())
                    .nick(member.getNick())
                    .phone(member.getPhone())
                    .sphone(member.getSphone())
//                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        }else {
            return ResponseEntity.badRequest().build();
        }


    }




}