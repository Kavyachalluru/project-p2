package com.revshop.order_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.revshop.order_service.model.OrderStatus;
import com.revshop.order_service.model.Orders;
import com.revshop.order_service.service.OrderService;

@Controller
@RequestMapping("/revshop")
public class OrderController {

	private OrderService orderService;
	
	@Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
	
	@GetMapping("/new")
    public String showCreateOrderForm(Model model) {
        model.addAttribute("order", new Orders());
        return "orders/PlaceOrder"; 
    }

    
	@PostMapping("/order")
	public String createOrder(@ModelAttribute("order") Orders order) {
        
		if (order.getStatus() == null) {
	        order.setStatus(OrderStatus.PENDING);
	    }
	    orderService.createOrder(order); // Save the order
	    return "redirect:/revshop/orders"; // Redirect to the order list
	}
	
	
//	@PostMapping("/order")
//	public ResponseEntity<Orders> createOrder(@RequestBody Orders order) {
//        if (order.getBuyerId() == null || order.getTotalAmount() == null) {
//            return ResponseEntity.badRequest().body(null);
//        }
//        Orders createdOrder = orderService.createOrder(order);
//        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
//    }
	
	@GetMapping("/orders")
	public String listOrders(Model model) {
	    List<Orders> ordersList = orderService.getAllOrders(); // Assuming this method exists in your service
	    model.addAttribute("orders", ordersList);
	    return "orders/ViewOrder"; // Return the view template for displaying orders
	}

    @GetMapping("/order/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Orders order = orderService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders orderDetails) {
        Orders updatedOrder = orderService.updateOrder(id, orderDetails);
        return updatedOrder != null ? ResponseEntity.ok(updatedOrder) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
