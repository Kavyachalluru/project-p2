package com.revshop.client_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.revshop.client_app.model.Seller;



@Controller
@RequestMapping("/revshop")
public class SellerViewController {
	
	@Autowired
	private RestTemplate restTemplate;

	// URL for the Seller Service to handle registration
	private static final String SELLER_SERVICE_URL = "http://localhost:8081/revshop/seller/register"; // Adjust as necessary

	@GetMapping("/register")
	public String showForm(Model model) {
		model.addAttribute("seller", new Seller());
		return "SellersReg"; // Thymeleaf template for seller registration
	}

	@PostMapping("/register")
	public String registerSeller(Model model, @ModelAttribute Seller seller) {
		// Send POST request to the Seller Service for registration
		ResponseEntity<String> response = restTemplate.postForEntity(SELLER_SERVICE_URL, seller, String.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			// Registration successful, redirect to login page
			return "redirect:/revshop/login";
		} else {
			// Registration failed, add error message to model
			model.addAttribute("message", "Registration failed. Please try again.");
			return "SellersReg"; // Return to the registration form
		}
	}
}
