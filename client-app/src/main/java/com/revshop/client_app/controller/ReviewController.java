package com.revshop.client_app.controller;

import com.revshop.client_app.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/revshop/reviews")
public class ReviewController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String REVIEW_SERVICE_URL = "http://localhost:8083/reviews";

    // Show the review form
    @GetMapping("/addForm")
    public String showReviewForm(@RequestParam Long productId, @RequestParam Long orderId, Model model) {
        model.addAttribute("productId", productId);
        model.addAttribute("orderId", orderId);

        // Fetch existing reviews for the product
        ResponseEntity<Review[]> response = restTemplate.getForEntity(REVIEW_SERVICE_URL + "/product/" + productId, Review[].class);
        List<Review> reviews = List.of(response.getBody());
        model.addAttribute("reviews", reviews);

        return "addReview"; // Returns the form to submit a review
    }

    // Submit a review
    @PostMapping("/add")
    public String submitReview(@ModelAttribute Review review) {
        restTemplate.postForEntity(REVIEW_SERVICE_URL, review, Review.class);
        return "redirect:/revshop/orderitems"; // Redirect back to order items after adding review
    }

    // Delete a review
    @PostMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id, @RequestParam Long productId, @RequestParam Long orderId) {
        String deleteUrl = REVIEW_SERVICE_URL + "/" + id; // Define the URL to call the review service to delete the review.
        restTemplate.delete(deleteUrl); // Use RestTemplate to delete the review.
        return "redirect:/revshop/reviews/addForm?productId=" + productId + "&orderId=" + orderId; // Redirect to a page showing the remaining reviews.
    }
}
