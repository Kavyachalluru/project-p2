package com.revshop.p2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revshop.p2.entity.Cart;
import com.revshop.p2.entity.Product;
	public interface CartRepository extends JpaRepository<Cart, Long> {

	    
	    List<Cart> findByBuyerId(Long buyerId);

	    Cart findByBuyerIdAndProductId(Long buyerId, Long productId);

	    void deleteByBuyerId(Long buyerId); 
	}
