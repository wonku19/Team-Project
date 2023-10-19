package com.kh.auction.controller;

import com.kh.auction.domain.*;
import com.kh.auction.domain.AuctionBoard;
import com.kh.auction.domain.Category;
import com.kh.auction.service.AuctionBoardService;
import com.kh.auction.service.CategoryService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
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

import java.util.*;
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
    private AuctionBoardService auctionBoardService;

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

        Page<AuctionBoard> result = auctionBoardService.showAll(pageable, builder);

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

    @GetMapping("/public/auction/{no}")
    public ResponseEntity<AuctionBoard> show(@PathVariable int no) {
        AuctionBoard auctionBoard = auctionBoardService.show(no);
        if (auctionBoard != null) {
            // 조회수 증가
            int currentViews = auctionBoard.getAuctionCheckNo();
            auctionBoard.setAuctionCheckNo(currentViews + 1);
            auctionBoardService.update(auctionBoard);

            return ResponseEntity.status(HttpStatus.OK).body(auctionBoard);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @PostMapping("/public/post")
    public ResponseEntity<AuctionBoard> create(@AuthenticationPrincipal String id, @RequestParam(name = "image", required = false) MultipartFile[] images, String title, String itemName, String desc, int sMoney, int eMoney, int gMoney, char nowBuy, String categoryNo) {
        AuctionBoard vo = new AuctionBoard();

        // 이미지 경로를 저장할 변수
        StringBuilder imagePaths = new StringBuilder();

        try {
            // 각 이미지 처리
            for (MultipartFile image : images) {
                String originalImage = image.getOriginalFilename();
                String realImage = originalImage.substring(originalImage.lastIndexOf("\\") + 1);
                String uuid = UUID.randomUUID().toString();
                String saveImage = uploadPath + File.separator + uuid + "_" + realImage;
                Path pathImage = Paths.get(saveImage);

                // 이미지를 서버에 저장
                image.transferTo(pathImage);

                // 이미지 경로를 imagePaths에 추가
                imagePaths.append(uuid).append("_").append(realImage).append(",");
            }

            // 마지막 쉼표 제거
            if (imagePaths.length() > 0) {
                imagePaths.deleteCharAt(imagePaths.length() - 1);
            }

            // 나머지 필드 설정
            vo.setAuctionTitle(title);
            vo.setItemName(itemName);
            vo.setItemDesc(desc);
            vo.setAuctionSMoney(sMoney);
            vo.setAuctionEMoney(eMoney);
            vo.setAuctionNowbuy(nowBuy);
            vo.setAuctionGMoney(gMoney);
            vo.setAuctionImg(imagePaths.toString());

            Category category = new Category();
            category.setCategoryNo(Integer.parseInt(categoryNo));
            vo.setCategory(category);

            vo.setAuctionDate(new Date());

            // AUCTION_END_DATE 설정 (AUCTION_DATE + 30일)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(vo.getAuctionDate());
//            calendar.add(Calendar.DAY_OF_MONTH, 30); // 30일 추가
            calendar.add(Calendar.MINUTE, 481); // 테스트
            vo.setAuctionEndDate(calendar.getTime());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(auctionBoardService.create(vo));
    }

    

    @PostMapping("/public/search")
    public ResponseEntity<AuctionBoardDTO> Search(@RequestBody RequestDTO request) {
        AuctionBoard auctionBoard = new AuctionBoard();
        // @RequestParam(name="page", defaultValue = "1") int page, @RequestParam(name="keyword",required = false) String keyword
        // required = false 를 주지 않았을땐 오류 났음
        log.info("request : " + request);
        int page = request.getPage();
        String keyword = request.getKeyword();
//        int categoryNo

        log.info("cc"+ category);
        log.info("keyword :: " + keyword);


        Sort sort = Sort.by("auctionNo").descending();
        Pageable pageable = PageRequest.of(page-1,5,sort);
        log.info("page :: " + pageable.getPageSize());
        Page<AuctionBoard> list = null;
        log.info("키워드 :: "+keyword);

        if(keyword == null){
            pageable = PageRequest.of(page-1,3,sort);
            list = auctionBoardService.showAll(pageable);
        }else{
            list = auctionBoardService.Search(keyword, pageable);
        }

        log.info(""+list.getContent());
        AuctionBoardDTO dto = new AuctionBoardDTO();
        dto.setContent(list.getContent());
        dto.setGetTotalPages(list.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(dto);
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



    // Hot 게시글
    @GetMapping("/public/auction/hot")
    public ResponseEntity<List<AuctionBoard>> HotList() {

        try {
            // 결과를 8개로 제한
            List<AuctionBoard> result = auctionBoardService.findByHot(8);
            log.info(""+result);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (Exception e){
            e.printStackTrace();
        }

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // New 게시글
    @GetMapping("/public/auction/new")
    public ResponseEntity<List<AuctionBoard>> NewList() {
        try {
            // 결과를 8개로 제한
            List<AuctionBoard> result = auctionBoardService.findByNew(8);
            log.info(""+result);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (Exception e){
            e.printStackTrace();
        }

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
