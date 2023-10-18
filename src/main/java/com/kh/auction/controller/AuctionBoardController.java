package com.kh.auction.controller;

import com.kh.auction.domain.AuctionBoard;
import com.kh.auction.domain.Category;
import com.kh.auction.domain.Comments;
import com.kh.auction.domain.Member;
import com.kh.auction.service.AuctionBoardService;
import com.kh.auction.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

//    @GetMapping("/auction")
//    public ResponseEntity<List<AuctionBoard>> showAll() {
//        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
//    }

    @PostMapping("/public/search")
    public ResponseEntity<AuctionBoardDTO> Search(@RequestBody RequestDTO request) {

        // @RequestParam(name="page", defaultValue = "1") int page, @RequestParam(name="keyword",required = false) String keyword

        log.info("request : " + request);

        int page = request.getPage();
        String keyword = request.getKeyword();

        log.info("keyword :: " + keyword);

        // required = false 를 주지 않았을땐 오류 났음
        Sort sort = Sort.by("auctionNo").descending();
        Pageable pageable = PageRequest.of(page-1,20,sort);
        Page<AuctionBoard> list = null;
        log.info("키워드 :: "+keyword);
    if(keyword == null){
        list = service.showAll(pageable);
    }else{
        list = service.Search(keyword, pageable);
    }
        log.info(""+list.getContent());
        AuctionBoardDTO dto = new AuctionBoardDTO();
        dto.setContent(list.getContent());
        dto.setTotalElements(list.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
//        return ResponseEntity.status(HttpStatus.OK).build();


//    }

    @GetMapping("/public/auction")
    public ResponseEntity<Map<String, Object>> BoardList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "category", required = false) Integer category,
            @RequestParam(name = "sortBy", required = false) String sortBy
    ) {
        Sort sort = Sort.by("auctionNo").descending();

        if ("biddingCount".equals(sortBy)) {
            sort = Sort.by(Sort.Direction.DESC, "AUCTION_CHECK_NO");
        }

        // 한 페이지의 10개
        Pageable pageable = PageRequest.of(page-1, 4, sort);

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
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
            calendar.add(Calendar.DAY_OF_MONTH, 30); // 30일 추가
            vo.setAuctionEndDate(calendar.getTime());

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