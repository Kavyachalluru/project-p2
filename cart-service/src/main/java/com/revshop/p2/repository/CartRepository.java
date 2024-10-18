package com.revshop.p2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revshop.p2.entity.Cart;
import com.revshop.p2.entity.Product;
	public interface CartRepository extends JpaRepository<Cart, Long> {

	    
		 // Find all cart items by buyer ID
	    List<Cart> findByBuyerId(Long buyerId);

	    // Find a cart item by buyer ID and product ID
	    Cart findByBuyerIdAndProductId(Long buyerId, Long productId);

	    // Delete all cart items for a specific buyer
	    void deleteByBuyerId(Long buyerId);
//		List<Cart> findByBuyer(Buyer buyer);
//	    Cart findByBuyerAndProduct(Buyer buyer, Product product);
//	        void deleteByBuyer(Buyer buyer);
	    

	    
	}
