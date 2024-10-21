package com.revshop.client_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.revshop.client_app.model.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {

    
	@Query(value = "SELECT * FROM cart WHERE buyer_id = :buyerId", nativeQuery = true)    
	List<Cart> findByBuyerId(Long buyerId);

    @Modifying
    @Query(value = "DELETE FROM cart WHERE buyer_id = :buyerId", nativeQuery = true)
    int deleteByBuyerId(Long buyerId);
    @Query(value = "SELECT * FROM cart WHERE buyer_id = :buyerId AND product_id = :productId", nativeQuery = true)   
    Cart findByBuyerIdAndProductId(Long buyerId, Long productId);
}
