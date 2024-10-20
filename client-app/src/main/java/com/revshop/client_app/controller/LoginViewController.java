package com.revshop.client_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.revshop.client_app.model.Buyer;
import com.revshop.client_app.model.Seller;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/revshop")
public class LoginViewController {

    @Autowired
    private RestTemplate restTemplate;

    // URL of the user service for validation
    private static final String BASE_URL = "http://localhost:8081/revshop"; // Adjust the URL as needed

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("message", "");
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        String loginUrl = BASE_URL + "/login?email=" + email + "&password=" + password;

        Seller s1 = new Seller();
        s1.setEmail(email);
        s1.setPassword(password);
        Buyer b1 = new Buyer();
        b1.setEmail(email);
        b1.setPassword(password);

        try {
            Seller seller = restTemplate.postForObject(loginUrl, s1, Seller.class);
            System.out.println("Seller response: " + seller.getEmail()); // Debug line

            if (seller.getBusinessName() != null) {
                session.setAttribute("loggedInUser", seller.getId());
                session.setAttribute("userType", "seller");
                return "redirect:/revshop/show"; 
            }

            Buyer buyer = restTemplate.postForObject(loginUrl, b1, Buyer.class);
            System.out.println("Buyer response: " + buyer.getEmail()); // Debug line

            if (buyer != null) {
                session.setAttribute("loggedInUser", buyer.getBuyer_id());
                session.setAttribute("userType", "buyer");
                return "redirect:/revshop/displayProducts"; 
            }

        } catch (Exception e) {
            model.addAttribute("message", "Error during login: " + e.getMessage());
            e.printStackTrace(); 
        }

        model.addAttribute("message", "Invalid login credentials");
        return "login"; 
    }



    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/revshop/login"; 
    }
}
