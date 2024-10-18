package com.revshop.order_service.service;

import com.revshop.order_service.model.Orders;
import com.revshop.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
	
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Orders createOrder(Orders order) {
    	order.setOrderDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    
    public Orders updateOrder(Long id, Orders orderDetails) {
        Orders order = getOrderById(id);
        if (order != null) {
            order.setBuyerId(orderDetails.getBuyerId());
            order.setTotalAmount(orderDetails.getTotalAmount());
            order.setStatus(orderDetails.getStatus());
            order.setBillingAddress(orderDetails.getBillingAddress());
            order.setShippingAddress(orderDetails.getShippingAddress());
            return orderRepository.save(order);
        }
        return null;
    }
}
