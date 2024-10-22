package com.revshop.userservice.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.userservice.Repository.SellerRepository;
import com.revshop.userservice.entity.Seller;

@Service
public class SellerService {

    private static final Logger logger = LoggerFactory.getLogger(SellerService.class);

    @Autowired
    SellerRepository repository;

    public Seller addSeller(Seller seller) {
        Seller savedSeller = repository.save(seller);
        logger.info("Added new seller with email: {}", seller.getEmail());
        return savedSeller;
    }

    public void updateSeller(Seller seller) {
        repository.save(seller);
        logger.info("Updated seller with email: {}", seller.getEmail());
    }

    public Optional<Seller> validateSeller(String email, String password) {
        logger.info("Validating seller with email: {}", email);
        Seller seller = repository.findByEmailAndPassword(email, password);
        if (seller != null) {
            logger.info("Seller validation successful for email: {}", email);
        } else {
            logger.warn("Seller validation failed for email: {}", email);
        }
        return Optional.ofNullable(seller);
    }

    public Seller getSellerByEmail(String email) {
        logger.info("Fetching seller by email: {}", email);
        Seller seller = repository.findByEmail(email).orElse(null);
        if (seller != null) {
            logger.info("Seller found with email: {}", email);
        } else {
            logger.warn("No seller found with email: {}", email);
        }
        return seller;
    }

    public Seller getSellerById(Long id) {
        logger.info("Fetching seller by ID: {}", id);
        Seller seller = repository.findById(id).orElse(null);
        if (seller != null) {
            logger.info("Seller found with ID: {}", id);
        } else {
            logger.warn("No seller found with ID: {}", id);
        }
        return seller;
    }
}
