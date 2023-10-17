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
import java.util.ArrayList;
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
    private CategoryService categoryService;

    @Value("${team.upload.path}") // application.properties에 있는 변수
    private String uploadPath;

    @GetMapping("/api/auction")
    public ResponseEntity<List<AuctionBoard>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(auctionBoardService.showAll());
    }

    @GetMapping("/auction/{no}")
    public ResponseEntity<AuctionBoard> show(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(auctionBoardService.show(no));
    }

    @PostMapping("/user/post")
    public ResponseEntity<AuctionBoard> create(@AuthenticationPrincipal String id, @RequestParam(name="image", required = false) MultipartFile image, String title, String itemName, String dece, int sMoney, int eMoney, int gMoney, char nowBuy, String categoryNo) {
    @GetMapping("/public/auction")
    public ResponseEntity<Map<String, Object>> BoardList(@RequestParam(name="page", defaultValue = "1") int page, @RequestParam(name="category", required = false) Integer category) {
        Sort sort = Sort.by("auctionNo").descending();

        // 한 페이지의 10개
        Pageable pageable = PageRequest.of(page-1, 5, sort);

        // 동적 쿼리를 위한 QuerlDSL을 사용한 코드들 추가

        // 1. Q도메인 클래스를 가져와야 한다.
        QAuctionBoard AuctionBoard = QAuctionBoard.auctionBoard;
        // 2. BooleanBuilder는 where문에 들어가는 조건들을 넣어주는 컨테이너
        BooleanBuilder builder = new BooleanBuilder();
        log.info("카테고리"+category);
        if(category!=null) {
            // 3. 원하는 조건은 필드값과 같이 결합해서 생성한다.
            BooleanExpression expression = QAuctionBoard.auctionBoard.category.categoryNo.eq(category);
//            BooleanExpression expression =

            // 4. 만들어진 조건은 where문에 and나 or 같은 키워드와 결합한다.
            builder.and(expression);
            log.info("불리언 빌더"+ builder.and(expression));
        }

        Page<AuctionBoard> result = service.showAll(pageable, builder);

        //log.info("Total Pages : " + result.getTotalPages()); // 총 몇 페이지
        //log.info("Total Count : " + result.getTotalElements()); // 전체 개수
        //log.info("Page Number : " + result.getNumber()); // 현재 페이지 번호
        //log.info("Page Size : " + result.getSize()); // 페이지당 데이터 개수
        //log.info("Next Page : " + result.hasNext()); // 다음 페이지가 있는지 존재 여부
        //log.info("First Page : " + result.isFirst()); // 시작 페이지 여부

        //return ResponseEntity.status(HttpStatus.OK).build();
        Map<String, Object> response = new HashMap<>();
        response.put("totalPages", result.getTotalPages()); // 추가: 총 페이지 수
        response.put("content", result.getContent());
        log.info("asd", result.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/public/post")
    public ResponseEntity<AuctionBoard> create(@AuthenticationPrincipal String id, @RequestParam(name="image", required = false) List<MultipartFile> image, String title, String itemName, String dece, int sMoney, int eMoney, int gMoney, char nowBuy, String categoryNo) {
        AuctionBoard vo = new AuctionBoard();
        try {
            // 이미지 업로드 처리
            // 이미지의 실제 파일 이름

            for(MultipartFile file : image){
                if(!file.isEmpty())
                {
                    String originalImage = file.getOriginalFilename();
                    String realImage = originalImage.substring(originalImage.lastIndexOf("\\")+1);
                    // UUID
                    String uuid = UUID.randomUUID().toString();

                    // 실제로 저장할 파일 명 (위치 포함)
                    String saveImage = uploadPath + File.separator + uuid + "_" + realImage;
                    Path pathImage = Paths.get(saveImage);

                    file.transferTo(pathImage);

                }
            }

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




}
}