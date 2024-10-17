package com.revshop.p1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.p1.exception.ResourceNotFoundException;

import com.revshop.p1.entity.Buyer;
import com.revshop.p1.entity.Product;
import com.revshop.p1.entity.Review;
import com.revshop.p1.repository.BuyerRepository;
import com.revshop.p1.repository.ProductRepository;
import com.revshop.p1.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    public Review addReview(Long productId, Long buyerId, String reviewText) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));

        Review review = new Review();
        review.setProduct(product);
        review.setBuyer(buyer);
        review.setReviewText(reviewText);

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
    
    public List<Review> getReviewsByProductAndBuyer(Long productId, Long buyerId) {
        return reviewRepository.findByProductIdAndBuyerId(productId, buyerId);
    }
}

