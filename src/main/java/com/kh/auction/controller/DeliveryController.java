package com.kh.auction.controller;

import com.kh.auction.domain.Delivery;
import com.kh.auction.service.DeliverySerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class DeliveryController {

    @Autowired
    private DeliverySerivce deliverySerivce;

    @GetMapping("/Delivery")
    public ResponseEntity<List<Delivery>> showAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(deliverySerivce.showAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/Delivery/{id}")
    public ResponseEntity<Delivery> show(@PathVariable int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(deliverySerivce.show(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/Delivery")
    public ResponseEntity<Delivery> create(@RequestBody Delivery delivery) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(deliverySerivce.create(delivery));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


    }
    @PutMapping("/Delivery")
    public ResponseEntity<Delivery> update(@RequestBody Delivery delivery){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(deliverySerivce.update(delivery));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/Delivery/{id}")
    public ResponseEntity<Delivery> delete(@PathVariable int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(deliverySerivce.delete(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
