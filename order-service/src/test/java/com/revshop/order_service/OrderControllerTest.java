package com.revshop.order_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.revshop.order_service.controller.OrderController;
import com.revshop.order_service.dto.OrdersDTO;
import com.revshop.order_service.model.Orders;
import com.revshop.order_service.service.OrderService;


import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlaceOrder_Success() {
        OrdersDTO ordersDTO = new OrdersDTO(); // populate DTO with test data
        Orders savedOrder = new Orders(); // create an Order object to return

        when(orderService.createOrder(any(OrdersDTO.class))).thenReturn(savedOrder);

        ResponseEntity<Orders> response = orderController.placeOrder(ordersDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedOrder, response.getBody());

        // Verify that the service method was called
        verify(orderService, times(1)).createOrder(ordersDTO);
    }

    @Test
    public void testViewOrdersByBuyer_Success() {
        Long buyerId = 1L;
        List<OrdersDTO> ordersDTOList = Collections.singletonList(new OrdersDTO()); // Create a list with one order DTO

        when(orderService.getOrdersByBuyerId(buyerId)).thenReturn(ordersDTOList);

        ResponseEntity<List<OrdersDTO>> response = orderController.viewOrdersByBuyer(buyerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ordersDTOList, response.getBody());

        // Verify that the service method was called
        verify(orderService, times(1)).getOrdersByBuyerId(buyerId);
    }

    @Test
    public void testViewOrdersByBuyer_NotFound() {
        Long buyerId = 1L;

        when(orderService.getOrdersByBuyerId(buyerId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<OrdersDTO>> response = orderController.viewOrdersByBuyer(buyerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        // Verify that the service method was called
        verify(orderService, times(1)).getOrdersByBuyerId(buyerId);
    }
}
