package com.revshop.userservice;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revshop.userservice.controller.LoginViewController;
import com.revshop.userservice.entity.Buyer;
import com.revshop.userservice.entity.Seller;
import com.revshop.userservice.service.BuyerService;
import com.revshop.userservice.service.SellerService;

import jakarta.servlet.http.HttpSession;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyerService buyerService;

    @MockBean
    private SellerService sellerService;

    @InjectMocks
    private LoginViewController loginViewController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginViewController).build();
    }

    @Test
    public void testSuccessfulSellerLogin() throws Exception {
        Seller seller = new Seller();
        seller.setId(1L); // Mock Seller ID

        when(sellerService.validateSeller(anyString(), anyString())).thenReturn(Optional.of(seller));

        mockMvc.perform(post("/revshop/login")
                .param("email", "seller@example.com")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(seller.getId()))
                // Check that the session has been set
                .andExpect(request().sessionAttribute("loggedInUser", seller.getId()));
    }

    @Test
    public void testSuccessfulBuyerLogin() throws Exception {
        Buyer buyer = new Buyer();
        buyer.setBuyer_id(1L); // Mock Buyer ID

        when(sellerService.validateSeller(anyString(), anyString())).thenReturn(Optional.empty());
        when(buyerService.validateBuyer(anyString(), anyString())).thenReturn(Optional.of(buyer));

        mockMvc.perform(post("/revshop/login")
                .param("email", "buyer@example.com")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buyer_id").value(buyer.getBuyer_id()))
                // Check that the session has been set
                .andExpect(request().sessionAttribute("loggedInUser", buyer.getBuyer_id()));
    }

    @Test
    public void testInvalidLogin() throws Exception {
        when(sellerService.validateSeller(anyString(), anyString())).thenReturn(Optional.empty());
        when(buyerService.validateBuyer(anyString(), anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/revshop/login")
                .param("email", "invalid@example.com")
                .param("password", "wrongpassword")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid login credentials"));
    }

    @Test
    public void testSellerNotFoundButBuyerFound() throws Exception {
        Buyer buyer = new Buyer();
        buyer.setBuyer_id(2L); // Mock Buyer ID

        when(sellerService.validateSeller(anyString(), anyString())).thenReturn(Optional.empty());
        when(buyerService.validateBuyer(anyString(), anyString())).thenReturn(Optional.of(buyer));

        mockMvc.perform(post("/revshop/login")
                .param("email", "buyer@example.com")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buyer_id").value(buyer.getBuyer_id()))
                // Check that the session has been set
                .andExpect(request().sessionAttribute("loggedInUser", buyer.getBuyer_id()));
    }
}
