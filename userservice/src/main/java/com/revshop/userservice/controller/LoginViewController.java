package com.revshop.userservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revshop.userservice.entity.Buyer;
import com.revshop.userservice.entity.Seller;
import com.revshop.userservice.service.BuyerService;
import com.revshop.userservice.service.SellerService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/revshop")
public class LoginViewController {
    
    @Autowired
    private BuyerService buyerService;
    
    @Autowired
    private SellerService sellerService;

    // Login endpoint for both buyers and sellers
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        // Check if the user is a seller
        Optional<Seller> seller = sellerService.validateSeller(email, password);
        if (seller.isPresent()) {
            session.setAttribute("loggedInUser", seller.get().getId());
            System.out.println("seller3########");// Store the seller in session
            return ResponseEntity.ok(seller);
        }
        
        // Check if the user is a buyer
        Optional<Buyer> buyer = buyerService.validateBuyer(email, password);
        if (buyer.isPresent()) {
            session.setAttribute("loggedInUser", buyer.get().getBuyer_id()); 
            System.out.println("buyer########");// Store the buyer in session
            return ResponseEntity.ok(buyer);
        }
        
        // Return error if neither found
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
    }

}
