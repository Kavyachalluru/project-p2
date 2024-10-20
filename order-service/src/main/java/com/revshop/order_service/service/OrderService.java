package com.revshop.order_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.revshop.order_service.dto.OrderItemDTO;
import com.revshop.order_service.dto.OrdersDTO;
import com.revshop.order_service.model.Buyer;
import com.revshop.order_service.model.OrderItems;
import com.revshop.order_service.model.Orders;
import com.revshop.order_service.model.Product;
import com.revshop.order_service.repository.OrderItemsRepository;
import com.revshop.order_service.repository.OrderRepository;
import com.revshop.order_service.repository.UserServiceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemsRepository orderItemRepository;
    
    @Autowired
    private UserServiceClient userServiceClient;
    
    @Autowired
    private RestTemplate restTemplate;

    // Convert OrderItemDTO to OrderItems entity
    private OrderItems convertToEntity(OrderItemDTO orderItemDTO) {
        OrderItems orderItem = new OrderItems();
        orderItem.setProductId(orderItemDTO.getProductId());  // Ensure the correct method is called
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setTotalPrice(orderItemDTO.getTotalPrice());
        return orderItem;
    }

    @Transactional
    public Orders createOrder(OrdersDTO orderDTO) {
    	logger.info("Received orderDTO: {}", orderDTO);
    	logger.info("Received orderDTO buyerId: {}", orderDTO.getBuyerId());
    	
    	// Check if the buyer exists
    	// Fetch the buyer details from the User Service using buyerId
        Buyer buyer = userServiceClient.getBuyerById(orderDTO.getBuyerId());
        if (buyer == null) {
            logger.error("Buyer with ID {} does not exist.", orderDTO.getBuyerId());
            throw new IllegalArgumentException("Buyer with ID " + orderDTO.getBuyerId() + " does not exist.");
        }
        logger.info("Retrieved Buyer: {}", buyer);
        Orders order = new Orders();
        order.setBuyerId(buyer.getBuyer_id()); // Set only the buyerId
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        
        logger.info("getting buyer id = {}", buyer.getBuyer_id());
//        Orders order = convertToEntity(orderDTO);
        logger.info("Attempting to save Order: {}", order);

        try {
            // Save the order
            Orders savedOrder = orderRepository.save(order);
            logger.info("Saved Order: {}", savedOrder);
            
            // Convert and save order items
            List<OrderItems> orderItems = new ArrayList<>();
            for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
                OrderItems orderItem = convertToEntity(orderItemDTO);
                orderItem.setOrder(savedOrder);  // Associate order with order items
                orderItems.add(orderItem);
            }
            orderItemRepository.saveAll(orderItems);
            
            return savedOrder;
        } catch (DataIntegrityViolationException e) {
            logger.error("Data Integrity Violation: {}", e.getMessage());
            throw e; 
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            throw e; 
        }
    }
     
    public OrdersDTO convertOrderToDTO(Orders order) {
        OrdersDTO dto = new OrdersDTO();
        dto.setId(order.getId());
        dto.setBuyerId(order.getBuyerId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setPaymentMethod(order.getPaymentMethod());

        // Convert OrderItems to OrderItemDTOs
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        for (OrderItems item : order.getOrderItems()) {
            try {
                String url = "http://localhost:8081/revshop/product/" + item.getProduct();
                Product product = restTemplate.getForObject(url, Product.class);

                if (product != null) {
                    OrderItemDTO itemDTO = new OrderItemDTO();
                    itemDTO.setId(item.getId());
                    itemDTO.setProductId(item.getProduct());
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setTotalPrice(item.getTotalPrice());
                    itemDTO.setOrderId(order.getId());

                    itemDTO.setProductName(product.getName());
                    itemDTO.setImageURL(product.getImage_url());
                    itemDTO.setBrand(product.getBrand());

                    orderItemDTOs.add(itemDTO);
                } else {
                    logger.error("Product details not found for product ID: {}", item.getProduct());
                }
            } catch (Exception e) {
                logger.error("Error occurred while fetching product details for product ID: {}", item.getProduct(), e);
            }
        }
        dto.setOrderItems(orderItemDTOs);

        return dto;
    }

    public List<OrdersDTO> getOrdersByBuyerId(Long buyerId) {
        List<Orders> orders = orderRepository.findByBuyerId(buyerId);
        List<OrdersDTO> ordersDTOList = new ArrayList<>();

        for (Orders order : orders) {
            OrdersDTO dto = convertOrderToDTO(order);
            ordersDTOList.add(dto);
        }

        return ordersDTOList;
     }
    
}
