package com.revshop.userservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.userservice.Repository.BuyerRepository;
import com.revshop.userservice.Repository.SellerRepository;
import com.revshop.userservice.entity.Buyer;
import com.revshop.userservice.entity.Seller;



@Service
public class loginService {
	
	 private static final Logger logger = LoggerFactory.getLogger(loginService.class);
	@Autowired
	private BuyerRepository br;
	@Autowired
	private SellerRepository sr;
	public Buyer loginAsBuyer(String email,String password) {
		logger.info("Attempting buyer login with email: {}", email);
		Buyer buyer = br.findByEmailAndPassword(email, password);
        if (buyer != null) {
            logger.info("Buyer login successful for email: {}", email);
        } else {
            logger.warn("Buyer login failed for email: {}", email);
        }
        return buyer;
    }
	public Seller loginAsSeller(String email,String password) {
		 logger.info("Attempting seller login with email: {}", email);
	        Seller seller = sr.findByEmailAndPassword(email, password);
	        if (seller != null) {
	            logger.info("Seller login successful for email: {}", email);
	        } else {
	            logger.warn("Seller login failed for email: {}", email);
	        }
	        return seller;
	    }

	}