package com.revshop.review_service;

import com.revshop.review_service.controller.ReviewController;
import com.revshop.review_service.model.Review;
import com.revshop.review_service.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReviewControllerTest {

    @Autowired
    private ReviewController reviewController;

    @MockBean
    private ReviewService reviewService;

    private Review review1;
    private Review review2;

    @BeforeEach
    public void setup() {
        review1 = new Review();
        review1.setId(1L);
        review1.setBuyerId(101L);
        review1.setProductId("P1001");
        review1.setRating(4);
        review1.setComment("Great product!");

        review2 = new Review();
        review2.setId(2L);
        review2.setBuyerId(102L);
        review2.setProductId("P1002");
        review2.setRating(5);
        review2.setComment("Excellent!");

        // Mocking the behavior of the service
        when(reviewService.createReview(review1)).thenReturn(review1);
        when(reviewService.getReviewsByProductId("P1001")).thenReturn(Arrays.asList(review1));
        when(reviewService.getReviewsByBuyerIdAndProductId(101L, "P1001")).thenReturn(Arrays.asList(review1));
    }

    @Test
    public void testCreateReview_Success() {
        ResponseEntity<Review> response = reviewController.createReview(review1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(review1, response.getBody());
        verify(reviewService, times(1)).createReview(review1);
    }

    @Test
    public void testGetReviewsByProductId_Success() {
        ResponseEntity<List<Review>> response = reviewController.getReviewsByProductId("P1001");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(review1, response.getBody().get(0));
        verify(reviewService, times(1)).getReviewsByProductId("P1001");
    }

    @Test
    public void testGetReviewsByBuyerIdAndProductId_Success() {
        ResponseEntity<List<Review>> response = reviewController.getReviewsByBuyerIdAndProductId(101L, "P1001");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(review1, response.getBody().get(0));
        verify(reviewService, times(1)).getReviewsByBuyerIdAndProductId(101L, "P1001");
    }

    @Test
    public void testDeleteReview_Success() {
        doNothing().when(reviewService).deleteReview(1L);

        ResponseEntity<Void> response = reviewController.deleteReview(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reviewService, times(1)).deleteReview(1L);
    }
}
