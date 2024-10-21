package com.revshop.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revshop.userservice.entity.Buyer;
import com.revshop.userservice.service.BuyerService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/revshop")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    // Buyer Registration
    @PostMapping("/buyerRegister")
    public ResponseEntity<String> registerBuyer(@RequestBody Buyer buyer) {
        buyerService.registerUser(buyer);
        return new ResponseEntity<>("Buyer registered successfully", HttpStatus.CREATED);
    }

    // Buyer Login (simulated)
    @PostMapping("/buyerLogin")
    public ResponseEntity<String> loginBuyer(HttpSession session, @RequestBody Buyer buyer) {
        // Simulate login, set session attributes, and handle login logic
        session.setAttribute("loggedInUser", buyer);  // Mock session management
        return new ResponseEntity<>("Buyer logged in successfully", HttpStatus.OK);
    }

    // Show Update Form for Buyer
    @GetMapping("/buyer/update")
    public ResponseEntity<?> showUpdateForm(HttpSession session) {
        Buyer buyer = (Buyer) session.getAttribute("loggedInUser");
        if (buyer != null) {
            return new ResponseEntity<>(buyer, HttpStatus.OK);
        }
        return new ResponseEntity<>("Buyer not logged in", HttpStatus.UNAUTHORIZED);
    }

    // Update Buyer Profile
    @PutMapping("/buyer/update")
    public ResponseEntity<String> updateBuyer(HttpSession session, @RequestBody Buyer updatedBuyer) {
        Buyer existingBuyer = (Buyer) session.getAttribute("loggedInUser");

        if (existingBuyer != null) {
            // Update the buyer's details
            existingBuyer.setFirstName(updatedBuyer.getFirstName());
            existingBuyer.setLastName(updatedBuyer.getLastName());
            existingBuyer.setPhoneNumber(updatedBuyer.getPhoneNumber());
            existingBuyer.setPassword(updatedBuyer.getPassword());

            buyerService.UpdateUser(existingBuyer);

            // Update session
            session.setAttribute("loggedInUser", existingBuyer);

            return new ResponseEntity<>("Buyer profile updated successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Buyer not found in session", HttpStatus.UNAUTHORIZED);
    }
    
    @GetMapping("/buyers/{id}")
    public ResponseEntity<Buyer> getBuyerById(@PathVariable Long id) {
        Buyer buyer = buyerService.getBuyerById(id); // Logic to find buyer by ID
        return new ResponseEntity<>(buyer, HttpStatus.OK);
    }

}
