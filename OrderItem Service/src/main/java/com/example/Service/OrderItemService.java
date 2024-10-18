package com.example.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Repository.OrderItemRepository;
import com.example.entity.OrderItem;
import com.example.exception.ResourceNotFoundException;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Save a new OrderItem
    public OrderItem saveOrderItem(OrderItem orderItem) {
        if (orderItem == null) {
            throw new IllegalArgumentException("Order item cannot be null.");
        }
        return orderItemRepository.save(orderItem);
    }

    // Retrieve a list of OrderItems by orderId
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        if (orderItems.isEmpty()) {
            throw new ResourceNotFoundException("No order items found for Order ID: " + orderId);
        }
        return orderItems;
    }

    // Update an existing OrderItem
    public OrderItem updateOrderItem(Long id, OrderItem orderItemDetails) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found for this ID: " + id));

        // Update the fields of the existing order item
        existingOrderItem.setQuantity(orderItemDetails.getQuantity());
        existingOrderItem.setPrice(orderItemDetails.getPrice());

        return orderItemRepository.save(existingOrderItem);
    }

    // Delete an OrderItem by ID
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found for this ID: " + id));

        orderItemRepository.delete(orderItem);
    }
}
