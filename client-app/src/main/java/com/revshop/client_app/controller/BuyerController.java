package com.revshop.client_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.revshop.client_app.model.Buyer;

@Controller
@RequestMapping("/revshop")
public class BuyerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/buyerRegister")
    public String showBuyerRegForm(Model model) {
        model.addAttribute("buyer", new Buyer()); // Changed to singular 'buyer' for clarity
        return "buyerReg"; // Ensure this matches the actual file name in resources/templates
    }

    @PostMapping("/buyerRegister")
    public String registerBuyer(Model model, @ModelAttribute("buyer") Buyer buyer) {
        String buyerServiceUrl = "http://localhost:8081/revshop/buyerRegister"; // URL for REST API

        try {
            // Send the buyer object to the REST API via RestTemplate
            ResponseEntity<String> response = restTemplate.postForEntity(buyerServiceUrl, buyer, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                // Successfully registered, redirect to login
                return "redirect:/revshop/login";
            } else {
                model.addAttribute("error", "Error registering buyer. Please try again.");
                return "buyerReg"; // Stay on the registration page with error message
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Service is currently unavailable. Please try again later.");
            return "buyerReg"; // Make sure this matches your template name
        }
    }
}
