package com.revshop.review_service.service;

import com.revshop.review_service.model.Review;
import com.revshop.review_service.repository.ReviewRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
	private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review createReview(Review review) {
    	 logger.info("Creating review for buyerId: {} and productId: {}", review.getBuyerId(), review.getProductId());
    	 Review savedReview = reviewRepository.save(review);
         logger.info("Review created successfully with id: {}", savedReview.getId());
         return savedReview;
     }

    public List<Review> getReviewsByProductId(String productId) {
        logger.info("Fetching reviews for productId: {}", productId);
        List<Review> reviews = reviewRepository.findByProductId(productId);
        logger.info("Found {} reviews for productId: {}", reviews.size(), productId);
        return reviews;
    }

    public List<Review> getReviewsByBuyerIdAndProductId(Long buyerId, String productId) {
    	logger.info("Fetching reviews for buyerId: {} and productId: {}", buyerId, productId);
    	List<Review> reviews = reviewRepository.findByBuyerIdAndProductId(buyerId, productId);
        logger.info("Found {} reviews for buyerId: {} and productId: {}", reviews.size(), buyerId, productId);
        return reviews;
    }

    public void deleteReview(Long id) {
    	logger.info("Deleting review with id: {}", id);
        reviewRepository.deleteById(id);
        logger.info("Review with id: {} deleted successfully", id);
    }
}
