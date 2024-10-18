package com.revshop.userservice.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.userservice.Repository.BuyerRepository;
import com.revshop.userservice.entity.Buyer;



@Service
public class BuyerService {
	
	@Autowired
	private BuyerRepository buyer_repo;
		
	public Buyer registerUser(Buyer buyer) {
		buyer.setRegistrationDate(LocalDateTime.now());
		return buyer_repo.save(buyer);
	}
	public Optional<Buyer> validateBuyer(String email, String password) {
        Buyer buyer = buyer_repo.findByEmailAndPassword(email,password);
        // Perform password validation (you may want to hash passwords in real scenarios)
        return Optional.ofNullable(buyer);
    }
	
	public Buyer getBuyerByEmail(String email) {
		return buyer_repo.findByEmail(email).get();
	}
	public Buyer UpdateUser(Buyer buyer) {
		buyer.setRegistrationDate(LocalDateTime.now());
		return buyer_repo.save(buyer);
	}
	
}
