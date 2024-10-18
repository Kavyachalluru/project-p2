package com.revshop.p2.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.revshop.p2.entity.Cart;
import com.revshop.p2.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Get all cart items for a buyer
    @GetMapping("/{buyerId}")
    public ResponseEntity<List<Cart>> getCartItems(@PathVariable Long buyerId) {
        List<Cart> cartItems = cartService.getCartItemsByBuyerId(buyerId);
        if (cartItems.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no cart items are found
        }
        return ResponseEntity.ok(cartItems); // Return 200 OK with the list of cart items
    }

    // Add a new item to the cart
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam Long buyerId, @RequestParam Long productId) {
        boolean added = cartService.addToCart(buyerId, productId);
        if (added) {
            return ResponseEntity.ok("Item added to cart successfully");
        }
        return ResponseEntity.badRequest().body("Failed to add item to cart");
    }

    // Remove an item from the cart
    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long cartId) {
        boolean removed = cartService.removeFromCart(cartId);
        if (removed) {
            return ResponseEntity.ok("Item removed from cart successfully");
        }
        return ResponseEntity.badRequest().body("Failed to remove item from cart");
    }

    // Update a cart item (e.g., change quantity)
    @PutMapping("/update")
    public ResponseEntity<String> updateCartItem(@RequestBody Cart cart) {
        boolean updated = cartService.updateCartItem(cart);
        if (updated) {
            return ResponseEntity.ok("Cart item updated successfully");
        }
        return ResponseEntity.badRequest().body("Failed to update cart item");
    }
}
//@Controller
//@RequestMapping("/revshop")
//public class CartController {
//
//    @Autowired
//    private CartService cartService;
//
//    @GetMapping("/cart")
//    public String getCartItems(HttpSession session, Model model) {
//        Buyer buyer = (Buyer) session.getAttribute("loggedInUser");
//        if (buyer == null) {
//            return "redirect:/revshop/login";
//        }
//
//        List<Cart> cartItems = cartService.getCartItemsByBuyer(buyer);
//        
//        // Calculate the total price
//        double totalPrice = 0.0;
//        for (Cart cart : cartItems) {
//            totalPrice += cart.getProduct().getDiscountPrice() * cart.getQuantity(); // Assuming getProduct() gives you the product and getPrice() returns the product's price
//        }
//        
//        model.addAttribute("cart", cartItems);
//        model.addAttribute("totalPrice", totalPrice); // Add the total price to the model
//        return "cart";	
//    }
//
//
//    // Add a new item to the cart
//    @PostMapping("/cart/add")
//    public String addToCart(@ModelAttribute Cart cart, HttpSession session,@RequestParam Long productId) {
//        Buyer buyer = (Buyer) session.getAttribute("loggedInUser");
//        if (buyer != null) {
//            cart.setBuyer(buyer); // Set the logged-in buyer
//            cartService.addToCart(buyer,productId); // Call the service to add the cart item
//        }
//        return "redirect:/revshop/cart"; // Redirect to the cart page after adding
//    }
//
//    // Remove an item from the cart
//    @GetMapping("/remove/{cartId}/{buyerId}")
//    public String removeFromCart(@PathVariable Long cartId, @PathVariable Long buyerId) {
//        cartService.removeFromCart(cartId);
//        return "redirect:/cart/" + buyerId; // Redirect to the cart page after removal
//    }
//
//    // Update the cart item (e.g., change quantity)
//    @PostMapping("/cart/update")
//    public String updateCartItem(@ModelAttribute Cart cart, HttpSession session) {
//        Buyer buyer = (Buyer) session.getAttribute("loggedInUser");
//        if (buyer != null) {
//            cart.setBuyer(buyer); // Ensure buyer is set before updating
//            cartService.updateCartItem(cart); // Call the service to update the cart item
//        }
//        return "redirect:/revshop/cart"; // Redirect to the cart page after updating
//    }
//
//    @PostMapping("/cart/remove")
//    public String removeFromCart(@RequestParam Long cartId, HttpSession session) {
//        Buyer buyer = (Buyer) session.getAttribute("loggedInUser");
//        if (buyer == null) {
//            return "redirect:/buyer/login";
//        }
//
//        cartService.removeFromCart(cartId); // Call the service to remove the item
//        return "redirect:/revshop/cart"; // Redirect back to the cart page after removal
//    }
//
//}
//
