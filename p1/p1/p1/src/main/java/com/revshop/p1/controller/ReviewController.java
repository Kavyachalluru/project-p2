package com.revshop.p1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revshop.p1.entity.Review;
import com.revshop.p1.service.ReviewService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Assuming buyerId can be obtained from the authentication context or request body
    @PostMapping("/add/{productId}/{buyerId}")
    public ResponseEntity<Review> addReview(
        @PathVariable Long productId, 
        @PathVariable Long buyerId, 
        @RequestParam String reviewText) {
        
        Review review = reviewService.addReview(productId, buyerId, reviewText);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
    
    

   
}