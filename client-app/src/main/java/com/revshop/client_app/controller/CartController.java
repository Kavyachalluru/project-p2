package com.revshop.client_app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.revshop.client_app.model.Cart;
import com.revshop.client_app.model.Product;
import com.revshop.client_app.repository.CartRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/revshop")
public class CartController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    CartRepository cartRepository;

    private static final String USER_SERVICE_URL = "http://localhost:8081/revshop"; // Replace with actual URL of user service
    private static final String CART_SERVICE_URL = "http://localhost:8082/revshop"; // Replace with actual URL of product service


    @GetMapping("/cart")
    public String getCartItems(HttpSession session, Model model) {
        Long buyerId = (Long) session.getAttribute("loggedInUser");
        System.out.println(buyerId);
        List<Cart> cartItems=cartRepository.findByBuyerId(buyerId);
//        ResponseEntity<Cart[]> response = restTemplate.getForEntity(
//                CART_SERVICE_URL + "/cart/" + buyerId, Cart[].class);
//        
//       List<Cart> cartItems = response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
        //System.out.println(cartItems.size()+"  "+cartItems.get(0).toString());
        for (Cart cart : cartItems) {
        	//System.out.println(cart.getProduct().getId()+">>>>>>>>>>cart productid");
            Product product = restTemplate.getForObject(USER_SERVICE_URL + "/product/" + cart.getProduct_id(), Product.class);
            cart.setProduct(product);
            cart.setPrice(product.getDiscountPrice());
        }

        double totalPrice = cartItems.stream()
                                      .mapToDouble(cart -> cart.getPrice() * cart.getQuantity())
                                      .sum();

        model.addAttribute("cart", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        
        return "cart"; 
    }

    @PostMapping("/cart/add")
    public String addToCart(@ModelAttribute Cart cart,HttpSession session,@RequestParam Long productId,Model model) {
        // Call the Cart Service to add the item to the cart
    	Long buyerId=(Long)session.getAttribute("loggedInUser");
    	String url=USER_SERVICE_URL+"/product/"+productId;
    	Product product = restTemplate.getForObject(url, Product.class);
    	//cart.setProduct(product);
    	System.out.println(product.getBrand());
    	
        HttpEntity<Cart> request = new HttpEntity<>(cart);
        restTemplate.postForEntity(CART_SERVICE_URL + "/cart/add?buyerId="+buyerId+"&productId=" + productId, request, Cart.class);
        System.out.println("posting to cart");
        model.addAttribute("product", product);
        return "redirect:/revshop/cart";
    }

    @PostMapping("/cart/update")
    public String updateCartItem(@ModelAttribute Cart cart) {
        // Call the Cart Service to update the cart item
        HttpEntity<Cart> request = new HttpEntity<>(cart);
        restTemplate.exchange(CART_SERVICE_URL + "/update", HttpMethod.PUT, request, Cart.class);
        return "redirect:/revshop/cart?buyerId=" + cart.getBuyer_id();
    }
    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long cartId, HttpSession session) {
    	Long buyerId=(Long)session.getAttribute("loggedInUser");
        restTemplate.delete(CART_SERVICE_URL + "/cart/remove/" + cartId);
        return "redirect:/revshop/cart";
    }
    @GetMapping("/checkout")
    public String showCart(Model model,@RequestParam("totalPrice") double totalPrice, HttpSession session) {
         model.addAttribute("totalPrice",totalPrice);
        return "orders"; // Return the view name for the cart page
    }
}