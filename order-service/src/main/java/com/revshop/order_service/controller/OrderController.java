package com.revshop.order_service.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.revshop.order_service.dto.OrderItemDTO;
import com.revshop.order_service.dto.OrdersDTO;
import com.revshop.order_service.model.Orders;
import com.revshop.order_service.service.OrderService; // Assume this service handles order-related logic

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/revshop")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Orders> placeOrder(@RequestBody OrdersDTO ordersDTO) {
    	
    	logger.info("Creating order with DTO: {}", ordersDTO);
        Orders savedOrder = orderService.createOrder(ordersDTO); 
        
        logger.info("Order created successfully: {}", savedOrder);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @GetMapping("/viewOrders/{buyerId}")
    public ResponseEntity<List<OrdersDTO>> viewOrdersByBuyer(@PathVariable Long buyerId){
    	List<OrdersDTO> orders = orderService.getOrdersByBuyerId(buyerId);
    	logger.info("order body : {}" + orders);
    	if(orders.isEmpty()) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	return new ResponseEntity<>(orders, HttpStatus.OK);
    }
       
    @GetMapping("/orders/{sellerId}")
    public ResponseEntity<List<OrdersDTO>> viewOrdersBySeller(@PathVariable Long sellerId) {
        List<OrdersDTO> orders = orderService.getOrdersBySellerId(sellerId); 
        logger.info("order body : {}" + orders);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
