package com.revshop.p2;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.revshop.p2.controller.CartController;
import com.revshop.p2.entity.Cart;
import com.revshop.p2.service.CartService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCartItems_Success() {
        Long buyerId = 1L;
        List<Cart> cartItems = Collections.singletonList(new Cart()); // Create a list with one cart item

        when(cartService.getCartItemsByBuyerId(buyerId)).thenReturn(cartItems);

        ResponseEntity<List<Cart>> response = cartController.getCartItems(buyerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cartItems, response.getBody());

        // Verify that the service method was called
        verify(cartService, times(1)).getCartItemsByBuyerId(buyerId);
    }

    @Test
    public void testGetCartItems_EmptyCart() {
        Long buyerId = 1L;

        when(cartService.getCartItemsByBuyerId(buyerId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Cart>> response = cartController.getCartItems(buyerId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        // Verify that the service method was called
        verify(cartService, times(1)).getCartItemsByBuyerId(buyerId);
    }

    @Test
    public void testAddToCart_Success() {
        Long buyerId = 1L;
        Long productId = 2L;
        Cart addedCartItem = new Cart(); // Create a cart item to return

        when(cartService.addToCart(buyerId, productId)).thenReturn(addedCartItem);

        ResponseEntity<?> response = cartController.addToCart(buyerId, productId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(addedCartItem, response.getBody());

        // Verify that the service method was called
        verify(cartService, times(1)).addToCart(buyerId, productId);
    }

    @Test
    public void testAddToCart_Failure() {
        Long buyerId = 1L;
        Long productId = 2L;

        when(cartService.addToCart(buyerId, productId)).thenReturn(null);

        ResponseEntity<?> response = cartController.addToCart(buyerId, productId);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Failed to add item to cart", response.getBody());

        // Verify that the service method was called
        verify(cartService, times(1)).addToCart(buyerId, productId);
    }

    @Test
    public void testRemoveFromCart_Success() {
        Long cartId = 1L;

        when(cartService.removeFromCart(cartId)).thenReturn(true);

        ResponseEntity<String> response = cartController.removeFromCart(cartId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Item removed from cart successfully", response.getBody());

        // Verify that the service method was called
        verify(cartService, times(1)).removeFromCart(cartId);
    }

    @Test
    public void testRemoveFromCart_Failure() {
        Long cartId = 1L;

        when(cartService.removeFromCart(cartId)).thenReturn(false);

        ResponseEntity<String> response = cartController.removeFromCart(cartId);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Failed to remove item from cart", response.getBody());

        // Verify that the service method was called
        verify(cartService, times(1)).removeFromCart(cartId);
    }

    @Test
    public void testUpdateCartItem_Success() {
        Cart cartItemToUpdate = new Cart(); // Create a cart item to update

        when(cartService.updateCartItem(cartItemToUpdate)).thenReturn(true);

        ResponseEntity<String> response = cartController.updateCartItem(cartItemToUpdate);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cart item updated successfully", response.getBody());

        // Verify that the service method was called
        verify(cartService, times(1)).updateCartItem(cartItemToUpdate);
    }

    @Test
    public void testUpdateCartItem_Failure() {
        Cart cartItemToUpdate = new Cart(); // Create a cart item to update

        when(cartService.updateCartItem(cartItemToUpdate)).thenReturn(false);

        ResponseEntity<String> response = cartController.updateCartItem(cartItemToUpdate);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Failed to update cart item", response.getBody());

        // Verify that the service method was called
        verify(cartService, times(1)).updateCartItem(cartItemToUpdate);
    }
}