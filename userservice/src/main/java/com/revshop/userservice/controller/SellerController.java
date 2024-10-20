package com.revshop.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revshop.userservice.entity.Seller;
import com.revshop.userservice.service.SellerService;

@RestController
@RequestMapping("/revshop")
public class SellerController {

    @Autowired
    private SellerService service;

    @PostMapping("/seller/register")
    public ResponseEntity<String> registerSeller(@RequestBody Seller seller) {
        service.addSeller(seller);
        return new ResponseEntity<>("Seller registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/seller/form")
    public ResponseEntity<Seller> showSellerForm() {
        return new ResponseEntity<>(new Seller(), HttpStatus.OK);
    }

    
}
