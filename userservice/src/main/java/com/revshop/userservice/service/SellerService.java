package com.revshop.userservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.userservice.Repository.SellerRepository;
import com.revshop.userservice.entity.Seller;



@Service
public class SellerService {
	@Autowired
	SellerRepository repository;
	public Seller addSeller(Seller seller) {
		return repository.save(seller);
	}
	public void updateSeller(Seller seller) {
		repository.save(seller);
	}
	public Optional<Seller> validateSeller(String email, String password) {
        Seller seller = repository.findByEmailAndPassword(email,password);
        return Optional.ofNullable(seller);
    }
	
	public Seller getSellerByEmail(String email) {
		return repository.findByEmail(email).get();
	}

	public Seller getSellerById(Long id) {
		return repository.findById(id).get();
	}
	
	
}