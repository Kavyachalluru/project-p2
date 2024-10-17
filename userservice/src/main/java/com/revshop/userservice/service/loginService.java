package com.revshop.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.userservice.Repository.BuyerRepository;
import com.revshop.userservice.Repository.SellerRepository;
import com.revshop.userservice.entity.Buyer;
import com.revshop.userservice.entity.Seller;



@Service
public class loginService {
	@Autowired
	private BuyerRepository br;
	@Autowired
	private SellerRepository sr;
	public Buyer loginAsBuyer(String email,String password) {
		return br.findByEmailAndPassword(email, password);
	}
	public Seller loginAsSeller(String email,String password) {
		return sr.findByEmailAndPassword(email, password);
	}
	

}