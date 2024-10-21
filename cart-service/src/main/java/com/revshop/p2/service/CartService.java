package com.revshop.p2.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revshop.p2.entity.Buyer;
import com.revshop.p2.entity.Cart;
import com.revshop.p2.entity.Product;
import com.revshop.p2.repository.CartRepository;

import java.util.List;

@Service
public class CartService {
	    @Autowired
	    private CartRepository cartRepository;

	    @Autowired
	    private RestTemplate restTemplate;

	    public List<Cart> getCartItemsByBuyerId(Long buyerId) {
	        return cartRepository.findByBuyerId(buyerId);
	    }

	    public Cart addToCart(Long buyerId, Long productId) {
	        if (buyerId != null && productId != null) {
	            Cart existingCartItem = cartRepository.findByBuyerIdAndProductId(buyerId, productId);
	            if (existingCartItem == null) {
	                // Create a new Cart item if it doesn't exist
	                Cart newCartItem = new Cart();
	                newCartItem.setBuyer_id(buyerId);
	                newCartItem.setProduct_id(productId);
	                newCartItem.setQuantity(1);
	                return cartRepository.save(newCartItem); // Return the newly created Cart object
	            } else {
	                // Update the existing Cart item
	                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
	                return cartRepository.save(existingCartItem); // Return the updated Cart object
	            }
	        }
	        return null; // Return null if buyerId or productId is invalid
	    }
	    // Remove an item from the cart
	    public boolean removeFromCart(Long cartId) {
	        if (cartRepository.existsById(cartId)) {
	            cartRepository.deleteById(cartId);
	            return true;
	        }
	        return false;
	    }	   
	    public boolean updateCartItem(Cart cart) {
	        if (cartRepository.existsById(cart.getId())) {
	            cartRepository.save(cart);
	            return true;
	        }
	        return false;
	    }
	}
