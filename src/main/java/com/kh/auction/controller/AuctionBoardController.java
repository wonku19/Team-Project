package com.kh.auction.controller;

import com.kh.auction.domain.*;
import com.kh.auction.repo.CategoryDAO;
import com.kh.auction.service.AuctionBoardService;
import com.kh.auction.service.CategoryService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins={"*"}, maxAge = 6000)
public class AuctionBoardController {

    @Autowired
    private AuctionBoardService auctionBoardService;

    @Autowired
    private CategoryService categoryService;

    @Value("${team.upload.path}") // application.properties에 있는 변수
    private String uploadPath;

    @GetMapping("/auction")
    public ResponseEntity<List<AuctionBoard>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(auctionBoardService.showAll());
    }

    @GetMapping("/auction/{no}")
    public ResponseEntity<AuctionBoard> show(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(auctionBoardService.show(no));
    }

    @PostMapping("/user/post")
    public ResponseEntity<AuctionBoard> create(@AuthenticationPrincipal String id, @RequestParam(name="image", required = false) MultipartFile image, String title, String itemName, String dece, int sMoney, int eMoney, int gMoney, char nowBuy, String categoryNo) {
        AuctionBoard vo = new AuctionBoard();
        try {
            // 이미지 업로드 처리
            // 이미지의 실제 파일 이름
            String originalImage = image.getOriginalFilename();
            String realImage = originalImage.substring(originalImage.lastIndexOf("\\")+1);

            // UUID
            String uuid = UUID.randomUUID().toString();

            // 실제로 저장할 파일 명 (위치 포함)
            String saveImage = uploadPath + File.separator + uuid + "_" + realImage;
            Path pathImage = Paths.get(saveImage);

            image.transferTo(pathImage);

            vo.setAuctionTitle(title);
            vo.setItemName(itemName);
            vo.setItemDesc(dece);
            vo.setAuctionSMoney(sMoney);
            vo.setAuctionEMoney(eMoney);
            vo.setAuctionNowbuy(nowBuy);
            vo.setAuctionGMoney(gMoney);
            vo.setAuctionImg(uuid + "_" + realImage);

            Category category = new Category();
            category.setCategoryNo(Integer.parseInt(categoryNo));
            vo.setCategory(category);

            Member member = new Member();
            member.setId(id); // @AuthenticationPrincipal String id 값 가져와서 넣기
            vo.setMemberId(member);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(auctionBoardService.create(vo));
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
    @GetMapping("/auction/{auctionNo}")
    public ResponseEntity<List<Category>> categoryList(@PathVariable int auctionNo){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findByAuctionNo(auctionNo));
    }



    // Hot 게시글
    @GetMapping("/public/auction/hot")
    public ResponseEntity<List<AuctionBoard>> hotList() {
        List<AuctionBoard> hotItems = auctionBoardService.findByCategoryHot();

        // 만약 항목이 8개 이상이면 처음 8개만 유지, 그렇지 않으면 모든 항목 반환
        hotItems = hotItems.size() > 8 ? hotItems.subList(0, 8) : hotItems;

        return ResponseEntity.status(HttpStatus.OK).body(hotItems);
    }


}
