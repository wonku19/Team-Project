package com.kh.auction.controller;

import com.kh.auction.domain.*;
import com.kh.auction.service.AuctionBoardService;
import com.kh.auction.service.CategoryService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins={"*"}, maxAge = 6000)
public class AuctionBoardController {

    @Autowired
    private AuctionBoardService service;

    @Autowired
    private CategoryService category;

    @Value("${team.upload.path}") // application.properties에 있는 변수
    private String uploadPath;


    @GetMapping("/public/auction")
    public ResponseEntity<Map<String, Object>> BoardList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "category", required = false) Integer category,
            @RequestParam(name = "sortOption", defaultValue = "1") int sortOption
    ) {
        // 정렬 방식에 따라 Sort 객체 생성
        Sort sort = getSortForOption(sortOption);

        Pageable pageable = PageRequest.of(page - 1, 3, sort);

        QAuctionBoard AuctionBoard = QAuctionBoard.auctionBoard;
        BooleanBuilder builder = new BooleanBuilder();

        if (category != null) {
            BooleanExpression expression = QAuctionBoard.auctionBoard.category.categoryNo.eq(category);
            builder.and(expression);
        }

        Page<AuctionBoard> result = service.showAll(pageable, builder);

        Map<String, Object> response = new HashMap<>();
        response.put("totalPages", result.getTotalPages());
        response.put("content", result.getContent());
        log.info(""+ result.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private Sort getSortForOption(int sortOption) {
        switch (sortOption) {
            case 1:
                return Sort.by("auctionAttendNo").descending(); // 입찰 횟수 내림차순
            case 2:
                return Sort.by("auctionCheckNo").descending(); // 조회수 내림차순
            case 3:
                return Sort.by("auctionNo").ascending(); // 등록순
            case 4:
                return Sort.by("currentPrice").ascending(); // 낮은 가격순
            case 5:
                return Sort.by("currentPrice").descending(); // 높은 가격순
            default:
                return Sort.by("auctionNo").descending(); // 기본값: 경매 번호 내림차순
        }
    }
    @GetMapping("/auction/{no}")
    public ResponseEntity<AuctionBoard> show(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(no));
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
        return ResponseEntity.status(HttpStatus.OK).body(service.create(vo));
    }

    @PutMapping("/auction")
    public ResponseEntity<AuctionBoard> update(@RequestBody AuctionBoard auctionBoard) {
        AuctionBoard result = service.update(auctionBoard);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/auction/{no}")
    public ResponseEntity<AuctionBoard> delete(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(no));
    }
    // 카테고리
    @GetMapping("/auction/{auctionNo}")
    public ResponseEntity<List<Category>> categoryList(@PathVariable int auctionNo){
        return ResponseEntity.status(HttpStatus.OK).body(category.findByAuctionNo(auctionNo));
    }




}
