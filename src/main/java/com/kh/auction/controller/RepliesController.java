package com.kh.auction.controller;

import com.kh.auction.domain.Replies;
import com.kh.auction.service.RepliesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class RepliesController {

    @Autowired
    private RepliesService service;

    @GetMapping("/replies")
    public ResponseEntity<List<Replies>> showAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
    }


    @GetMapping("/replies/{id}")
    public ResponseEntity<Replies> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
    }

    @PostMapping("/replies")
    public ResponseEntity<Replies> create(@PathVariable Replies replies) {
        return ResponseEntity.status(HttpStatus.OK).body(service.create(replies));
    }

    @PutMapping("/replies")
    public ResponseEntity<Replies> update(@PathVariable Replies replies) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(replies));
    }

    @DeleteMapping("/replies/{id}")
    public ResponseEntity<Replies> delete(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }
    
}
