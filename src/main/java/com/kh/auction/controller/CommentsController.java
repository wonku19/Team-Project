package com.kh.auction.controller;

import com.kh.auction.domain.Comments;
import com.kh.auction.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class CommentsController {

    @Autowired
    private CommentsService service;

    @GetMapping("/comments")
    public ResponseEntity<List<Comments>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
    }


    @GetMapping("/comments/{id}")
    public ResponseEntity<Comments> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    @PostMapping("/comments")
    public ResponseEntity<Comments> create(@PathVariable Comments comments) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(comments));
    }

    @PutMapping("/comments")
    public ResponseEntity<Comments> update(@PathVariable Comments comments) {
        Comments result = service.update(comments);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(result));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Comments> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }

}
