package com.kh.auction.controller;

import com.kh.auction.domain.AuctionBoard;
import com.kh.auction.domain.Category;
import com.kh.auction.domain.Member;
//import com.kh.auction.domain.MemberDTO;
import com.kh.auction.domain.MemberDTO;
import com.kh.auction.security.JwtAuthenticationFilter;
import com.kh.auction.security.TokenProvider;
import com.kh.auction.security.WebSecurityConfig;
import com.kh.auction.service.AuctionBoardService;
import com.kh.auction.service.CategoryService;
import com.kh.auction.service.MemberService;
import jakarta.servlet.http.Cookie;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private AuctionBoardService auctionBoardService;

    @Autowired
    private CategoryService categoryService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @GetMapping("/public")
    public ResponseEntity<List<Member>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.showAll());
    }

    @GetMapping("/user/show")
    public ResponseEntity<Member> show(@AuthenticationPrincipal String id) {
        Member member = memberService.show(id);
        try{
            if(member.getAuthority().equals("ROLE_USER")){
                return ResponseEntity.status(HttpStatus.OK).body(memberService.show(id));
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // 유저들 회원가입
    @PostMapping("/public/create")
    public ResponseEntity UserCreate(@RequestBody MemberDTO dto) {
        Member member = Member.builder()
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .nick(dto.getNick())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .sphone(dto.getSphone())
                .addr(dto.getAddr())
                .birthday(dto.getBirthday())
                .authority("ROLE_USER")
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

                .email(registerMember.getEmail())
                .sphone(registerMember.getSphone())
                .authority(registerMember.getAuthority())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<Member> userShow(@AuthenticationPrincipal String id) {
        Member member = new Member();
        member.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.show(id));
    }

    // 어드민 회원가입
    @PostMapping("/public/admin/create")
    public ResponseEntity AdminCreate(@RequestBody MemberDTO dto) {
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
                .authority("ROLE_ADMIN")
                .build();
        log.info("멤버"+member);
        // 서비스를 이용해 리포지터리에 유저 저장
        Member registerMember = memberService.create(member);
        MemberDTO responseDTO = dto.builder()
                .id(registerMember.getId())
                .name(registerMember.getName())
                .addr(registerMember.getAddr())
                .nick(registerMember.getNick())
                .email(registerMember.getEmail())
                .phone(registerMember.getPhone())
                .sphone(registerMember.getSphone())
                .authority(registerMember.getAuthority())
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
                    .email(member.getEmail())
                    .phone(member.getPhone())
                    .sphone(member.getSphone())
                    .token(token)
                    .point(member.getPoint())
                    .authority(member.getAuthority())
                    .email(member.getEmail())
                    .singupDate(member.getSingupDate())
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }


    // 비밀번호 수정
    @PutMapping("/public/updatePassword")
    public ResponseEntity<Member> updatePassword(@RequestBody MemberDTO dto){
        String id = dto.getId();
        String password = dto.getPassword();
        String birthday = dto.getBirthday();
        Member existMember = memberService.show(id);
        if(existMember !=null){
            if(existMember.getBirthday().equals(birthday)){
                existMember.setPassword(password);
                Member result = memberService.passwordUpdate(id,password);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).build();

    }
    // 포인트 api
    @PutMapping("/user/point")
    public ResponseEntity<Member> updatePoint(@AuthenticationPrincipal String id, @RequestBody Member member) {
        int point = member.getPoint();
        Member existMember = memberService.show(id);
        if (existMember != null) {
            existMember.setPoint(existMember.getPoint()+ point);
            log.info(existMember.getPoint()+"ss"+point);
            Member result = memberService.update(id,existMember.getPoint());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    //최종입찰
    @PutMapping("/user/buyerPoint")
    public ResponseEntity<Member> updateBuyerPoint(@AuthenticationPrincipal String id, @RequestParam int point) {
        Member existMember = memberService.show(id);
        if (existMember != null) {
            existMember.setPoint(point);
            log.info(existMember.getPoint()+"ss"+point);
            Member result = memberService.update(id,existMember.getPoint());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/public/updateCategory")
    public ResponseEntity updateCategory(@RequestParam int no){
        AuctionBoard auctionBoard = auctionBoardService.show(no);
        if (auctionBoard != null) {
            auctionBoard = auctionBoardService.updateCategoryNo(no,'Y');
            log.info(auctionBoard+"");
            return ResponseEntity.status(HttpStatus.OK).body(auctionBoard);
            }
        return null;
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


    // 비밀번호 확인
    @PostMapping("/user/pwdChack")
    public ResponseEntity<Member> chackPassword(@AuthenticationPrincipal String id ,@RequestBody MemberDTO dto) {
        Member existMember = memberService.show(id);
        String password = dto.getPassword();
        if(existMember.getPassword() !=null){
            if(existMember.getPassword().equals(password)){
                existMember.setPassword(password);
            Member result = memberService.passwordUpdate(id,password);
            return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    // 비밀번호 변경
    @PutMapping("/user/pwdUp")
    public ResponseEntity<Member> updatePassword(@AuthenticationPrincipal String id ,@RequestBody MemberDTO dto){
        Member existMember = memberService.show(id);
        String password = dto.getPassword();
        if(existMember.getPassword() !=null){
//            if(existMember.getPassword().equals(password)){
//                existMember.setPassword(password);
                Member result = memberService.passwordUpdate(id,password);
                result.setPassword(passwordEncoder.encode(dto.getPassword()));
                return ResponseEntity.status(HttpStatus.OK).body(result);
//            }
        }
        return ResponseEntity.status(HttpStatus.OK).build();

    }



    // 포인트 충전 내역
//    @PutMapping("/user/pointAdd")
//    public ResponseEntity

    // 내 결제 기록 최신순
//    @GetMapping("public/mypayList/new")
//    public ResponseEntity<List<>>

    // 결제 기록 페이지
//    @GetMapping("/user/pointList")
//    public ResponseEntity<>

}