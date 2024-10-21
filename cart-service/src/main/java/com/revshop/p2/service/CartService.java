package com.revshop.p2.service;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(CartService.class); 
	    @Autowired
	    private CartRepository cartRepository;

	    @Autowired
	    private RestTemplate restTemplate;

	    public List<Cart> getCartItemsByBuyerId(Long buyerId) {
	    	 logger.info("Fetching cart items for buyerId: {}", buyerId);
	         List<Cart> cartItems = cartRepository.findByBuyerId(buyerId);
	         logger.debug("Number of cart items found for buyerId {}: {}", buyerId, cartItems.size());
	         return cartItems;
	     }


	    public Cart addToCart(Long buyerId, Long productId) {
	    	logger.info("Adding to cart - BuyerId: {}, ProductId: {}", buyerId, productId);
	        if (buyerId != null && productId != null) {
	            Cart existingCartItem = cartRepository.findByBuyerIdAndProductId(buyerId, productId);
	            if (existingCartItem == null) {
	            	logger.debug("No existing cart item found. Creating new item for BuyerId: {}, ProductId: {}", buyerId, productId);
	                // Create a new Cart item if it doesn't exist
	                Cart newCartItem = new Cart();
	                newCartItem.setBuyer_id(buyerId);
	                newCartItem.setProduct_id(productId);
	                newCartItem.setQuantity(1);
	                Cart savedCart = cartRepository.save(newCartItem);
	                logger.debug("New cart item saved for BuyerId: {} with ProductId: {}", buyerId, productId);
	                return savedCart;
	            } else {
	                logger.debug("Existing cart item found. Updating quantity for BuyerId: {}, ProductId: {}", buyerId, productId);
	                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
	                Cart updatedCart = cartRepository.save(existingCartItem);
	                logger.debug("Updated cart item saved for BuyerId: {} with ProductId: {}", buyerId, productId);
	                return updatedCart;
	            }
	        }
	        logger.warn("Invalid buyerId or productId provided for adding to cart.");
	        return null;
	    }
	    // Remove an item from the cart
	    public boolean removeFromCart(Long cartId) {
	        if (cartRepository.existsById(cartId)) {
	            cartRepository.deleteById(cartId);
	            logger.debug("Item removed from cart with CartId: {}", cartId);
	            return true;
	        }
	        logger.warn("Cart item with CartId: {} does not exist.", cartId);
	        return false;
	    }	   
	    public boolean updateCartItem(Cart cart) {
	        if (cartRepository.existsById(cart.getId())) {
	            cartRepository.save(cart);
	            logger.debug("Cart item updated with CartId: {}", cart.getId());
	            return true;
	        }
	        logger.warn("Cart item with CartId: {} does not exist.", cart.getId());
	        return false;
	    }
	}
