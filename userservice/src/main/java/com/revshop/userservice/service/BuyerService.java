package com.revshop.userservice.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.userservice.Repository.BuyerRepository;
import com.revshop.userservice.entity.Buyer;

@Service
public class BuyerService {

    private static final Logger logger = LoggerFactory.getLogger(BuyerService.class);

    @Autowired
    private BuyerRepository buyer_repo;

    public Buyer registerUser(Buyer buyer) {
        buyer.setRegistrationDate(LocalDateTime.now());
        Buyer savedBuyer = buyer_repo.save(buyer);
        logger.info("Registered new buyer with email: {}", buyer.getEmail());
        return savedBuyer;
    }

    public Optional<Buyer> validateBuyer(String email, String password) {
        logger.info("Validating buyer with email: {}", email);
        Buyer buyer = buyer_repo.findByEmailAndPassword(email, password);
        if (buyer != null) {
            logger.info("Buyer validation successful for email: {}", email);
        } else {
            logger.warn("Buyer validation failed for email: {}", email);
        }
        return Optional.ofNullable(buyer);
    }

    public Buyer getBuyerByEmail(String email) {
        logger.info("Fetching buyer by email: {}", email);
        Buyer buyer = buyer_repo.findByEmail(email).orElse(null);
        if (buyer != null) {
            logger.info("Buyer found with email: {}", email);
        } else {
            logger.warn("No buyer found with email: {}", email);
        }
        return buyer;
    }

    public Buyer updateUser(Buyer buyer) {
        buyer.setRegistrationDate(LocalDateTime.now());
        Buyer updatedBuyer = buyer_repo.save(buyer);
        logger.info("Updated buyer with email: {}", buyer.getEmail());
        return updatedBuyer;
    }

    public Buyer getBuyerById(Long id) {
        logger.info("Fetching buyer by ID: {}", id);
        Buyer buyer = buyer_repo.findById(id).orElse(null);
        if (buyer != null) {
            logger.info("Buyer found with ID: {}", id);
        } else {
            logger.warn("No buyer found with ID: {}", id);
        }
        return buyer;
    }

    public Buyer loginUser(Buyer buyer) {
        // Assuming you have a method in your repository to find a buyer by email and password
        return buyer_repo.findByEmailAndPassword(buyer.getEmail(), buyer.getPassword());
    }
}


