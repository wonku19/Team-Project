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

    @GetMapping("/Replies")
    public ResponseEntity<List<Replies>> showAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/Replies/{id}")
    public ResponseEntity<Replies> show(@PathVariable int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.show(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/Replies")
    public ResponseEntity<Replies> create(@RequestBody Replies replies) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.create(replies));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/Replies")
    public ResponseEntity<Replies> update(@RequestBody Replies replies) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(replies));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("Replies/{id}")
    public ResponseEntity<Replies> delete(@PathVariable int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}