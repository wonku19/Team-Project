package com.kh.auction.controller;

import com.kh.auction.domain.Category;
import com.kh.auction.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.showAll());
    }

    @GetMapping("/category/{no}")
    public ResponseEntity<Category> show(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.show(no));
    }

    @PostMapping("/category")
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.create(category));
    }

    @PutMapping("/category")
    public ResponseEntity<Category> update(@RequestBody Category category) {
        Category result = categoryService.update(category);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @DeleteMapping("/category/{no}")
    public ResponseEntity<Category> delete(@PathVariable int no) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.delete(no));
    }


}
