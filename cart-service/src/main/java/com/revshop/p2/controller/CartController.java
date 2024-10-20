package com.revshop.p2.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.revshop.p2.entity.Cart;
import com.revshop.p2.entity.Product;
import com.revshop.p2.service.CartService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/revshop")
public class CartController {
	 @Autowired
	    private RestTemplate restTemplate;

    @Autowired
    private CartService cartService;
    private static final String USER_SERVICE_URL = "http://localhost:8081/revshop"; // Replace with actual URL of user service
    @GetMapping("/cart/{buyerId}")
    public ResponseEntity<List<Cart>> getCartItems(@PathVariable Long buyerId) {
    	System.out.println("cart1???///////");
        List<Cart> cartItems = cartService.getCartItemsByBuyerId(buyerId);
        if (cartItems.isEmpty()) {
        	System.out.println("entering cart");
            return ResponseEntity.ok(new ArrayList<>());
        }
        System.out.println("ok ---------");
        return ResponseEntity.ok(cartItems); 
    }

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestParam Long buyerId, @RequestParam Long productId) {
//    	String url=USER_SERVICE_URL+"/product/"+productId;
//    	Product product = restTemplate.getForObject(url, Product.class);
        Cart added = cartService.addToCart(buyerId, productId);
        if (added!=null) {
        	System.out.println("item json is triggered '''''''''''");
            return ResponseEntity.ok(added);
        }
        return ResponseEntity.badRequest().body("Failed to add item to cart");
    }

    @DeleteMapping("/cart/remove/{cartId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long cartId) {
        boolean removed = cartService.removeFromCart(cartId);
        if (removed) {
            return ResponseEntity.ok("Item removed from cart successfully");
        }
        return ResponseEntity.badRequest().body("Failed to remove item from cart");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCartItem(@RequestBody Cart cart) {
        boolean updated = cartService.updateCartItem(cart);
        if (updated) {
            return ResponseEntity.ok("Cart item updated successfully");
        }
        return ResponseEntity.badRequest().body("Failed to update cart item");
    }
}
