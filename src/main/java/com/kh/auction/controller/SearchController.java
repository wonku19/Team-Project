package com.kh.auction.controller;

import com.kh.auction.domain.Search;
import com.kh.auction.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<List<Search>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.showAll());
    }

    @GetMapping("/search/{searchSeq}")
    public ResponseEntity<Search> show(@PathVariable int searchSeq) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.show(searchSeq));
    }

    @PostMapping("/search")
    public ResponseEntity<Search> create(@RequestBody Search search) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.create(search));
    }

    @PutMapping("/search")
    public ResponseEntity<Search> update(@RequestBody Search search) {
        Search result = searchService.update(search);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/search/{searchSeq}")
    public ResponseEntity<Search> delete(@PathVariable int searchSeq) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.delete(searchSeq));
    }
}