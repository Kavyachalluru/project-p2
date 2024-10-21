package com.revshop.client_app.controller;

import com.revshop.client_app.dto.OrderItemDTO;
import com.revshop.client_app.dto.OrdersDTO;
import com.revshop.client_app.model.Buyer;
import com.revshop.client_app.model.OrderItems;
//import com.revshop.client_app.model.OrderRequest; // Create a model for order request
import com.revshop.client_app.model.Orders; 
import com.revshop.client_app.model.Product;

import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/revshop")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);  
	private final SimpMessagingTemplate messagingTemplate;
   public OrderController (SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

	
    @Autowired
    private RestTemplate restTemplate;
    
    private static final String ORDER_SERVICE_URL = "http://localhost:9090/revshop";

    @GetMapping("/orderform")
    public String showOrderForm(@RequestParam Long productId, 
                                @RequestParam Double price,
                                @RequestParam(required = false) String productImage,
                                @RequestParam String productName,
                                Model model) {

        // Create a new order instance
    	System.out.println("Buy:::"+productId);
        Orders order = new Orders(); 
        order.setOrderItems(new ArrayList<>());
        order.setTotalPrice(price);
        
        // Use RestTemplate to get the product details from the ProductService
        String productServiceUrl = "http://localhost:8081/revshop/product/" + productId;
        Product product = restTemplate.getForObject(productServiceUrl, Product.class);
       logger.info(product.getName());
        // Store the product in the session for future use
        model.addAttribute("product", product);
        
        // Add order and product details to the model
        model.addAttribute("order", order); // Add the order object to the model
        model.addAttribute("productId", productId);
        model.addAttribute("price", price);
        model.addAttribute("productName", productName);

        // Optional: Check if productImage is present and add to the model
        if (productImage != null) {
            model.addAttribute("productImage", productImage);
        } else {
            model.addAttribute("productImage", "default-image.png"); // Fallback or placeholder image
        }

        return "orders2"; // Return the view name for the order form page
    }

    @PostMapping("/addorders")
    public String submitOrder(
            @RequestParam Long productId,
            @RequestParam Double totalPrice,
            @RequestParam String shippingAddress,
            @RequestParam String paymentMethod,
            Model model, HttpSession session) {

        // Retrieve buyer ID from session
        Long buyerId = (Long) session.getAttribute("loggedInUser");
        if (buyerId == null) {
            model.addAttribute("message", "You need to log in to place an order.");
            return "redirect:/revshop/login";
        }

        // Use RestTemplate to get the product details
        String productServiceUrl = "http://localhost:8081/revshop/product/" + productId;
        ResponseEntity<Product> productResponse = restTemplate.getForEntity(productServiceUrl, Product.class);

        if (productResponse.getStatusCode() != HttpStatus.OK) {
            model.addAttribute("message", "Product not found. Please try again.");
            return "redirect:/revshop/products";
        }

        Product product = productResponse.getBody();

        // Create and populate OrderDTO
        OrdersDTO orderDto = new OrdersDTO();
        orderDto.setBuyerId(buyerId);
        orderDto.setTotalPrice(totalPrice);
        orderDto.setShippingAddress(shippingAddress);
        orderDto.setPaymentMethod(paymentMethod);

        // Create and populate OrderItemDTO
        OrderItemDTO orderItemDto = new OrderItemDTO();
        orderItemDto.setProductId(productId);
        orderItemDto.setTotalPrice(totalPrice);
        orderItemDto.setQuantity(1); // Adjust as necessary

        // Add OrderItemDTO to OrderDTO
        List<OrderItemDTO> orderItems = new ArrayList<>();
        orderItems.add(orderItemDto);
        orderDto.setOrderItems(orderItems);

        // Call the OrderService API
        String orderServiceUrl = "http://localhost:9090/revshop/place"; // Adjust URL as necessary
        ResponseEntity<Orders> response = restTemplate.postForEntity(orderServiceUrl, orderDto, Orders.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
        	 messagingTemplate.convertAndSend("/topic/notifications", 
        	             product.getName() + " has been purchased!");
            model.addAttribute("message", "Order placed successfully!");
            return "redirect:/revshop/displayProducts";
        } else {
            model.addAttribute("message", "Failed to place order. Please try again.");
            return "orders2";
        }
    }

    @GetMapping("/orderitems")
    public String showOrdersToBuyer(Model model, HttpSession session) {
    	Long buyerId = (Long) session.getAttribute("loggedInUser");
    	
        if (buyerId == null) {
            model.addAttribute("message", "You need to log in to place an order.");
            return "redirect:/revshop/login";
        }
        
    	String orderServiceUrl = ORDER_SERVICE_URL + "/viewOrders/" + buyerId;
    	ResponseEntity<List<OrdersDTO>> response = restTemplate.exchange(orderServiceUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<OrdersDTO>>() {});
    	
    	if (response.getStatusCode().is2xxSuccessful()) {
            List<OrdersDTO> orders = response.getBody();
            if (orders == null || orders.isEmpty()) {
                // Log if orders list is empty
                logger.info("No orders found for buyerId: {}" + buyerId);
            } else {
                // Log orders data for debugging
                logger.info("Orders found: {}" + orders);
            }
            model.addAttribute("orders", orders);
        } else {
            model.addAttribute("message", "Unable to retrieve orders.");
        }

    	
    	return "orderitems";
    }
    
    
    @PostMapping("updateOrder/{orderId}")
    public String updateOrder(@PathVariable Long orderId, @ModelAttribute OrdersDTO orderDTO,  HttpSession session, Model model) {
    	
    	Long buyerId = (Long) session.getAttribute("loggedInUser");
        
        // Ensure the buyer is logged in
        if (buyerId == null) {
            model.addAttribute("message", "You need to log in to update the order.");
            return "redirect:/revshop/login";
        }
        orderDTO.setId(orderId);
        // Call OrderService to update the order
        String updateUrl = ORDER_SERVICE_URL + "/updateOrder/" + orderId;
        restTemplate.put(updateUrl, orderDTO);

        return "redirect:/revshop/orderitems"; // Redirect to the order items page
    }
    	
    
}
