package com.revshop.p2.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

	    @Value("${product.service.url}")
	    private String productServiceUrl;

	    // Get all cart items for a buyer
	    public List<Cart> getCartItemsByBuyerId(Long buyerId) {
	        return cartRepository.findByBuyerId(buyerId);
	    }

	    // Add a product to the cart
	    public boolean addToCart(Long buyerId, Long productId) {
	        Product product = restTemplate.getForObject(productServiceUrl + "/" + productId, Product.class);
	        if (product != null) {
	            Cart existingCartItem = cartRepository.findByBuyerIdAndProductId(buyerId, productId);
	            if (existingCartItem == null) {
	                Cart newCartItem = new Cart();
	                newCartItem.setBuyerId(buyerId);
	                newCartItem.setProductId(productId);
	                newCartItem.setQuantity(1);
	                cartRepository.save(newCartItem);
	            } else {
	                existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
	                cartRepository.save(existingCartItem);
	            }
	            return true;
	        }
	        return false;
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
