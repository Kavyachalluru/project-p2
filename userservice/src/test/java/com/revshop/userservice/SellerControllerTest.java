package com.revshop.userservice;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revshop.userservice.controller.SellerController;
import com.revshop.userservice.entity.Seller;
import com.revshop.userservice.service.SellerService;

@SpringBootTest
@AutoConfigureMockMvc
public class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerService sellerService;

    @InjectMocks
    private SellerController sellerController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sellerController).build();
    }

    @Test
    public void testRegisterSeller() throws Exception {
        Seller seller = new Seller(); // Set up seller details
        seller.setId(1L); // Mock Seller ID

        // Mock the service method
        when(sellerService.addSeller(any(Seller.class))).thenReturn(seller);

        mockMvc.perform(post("/revshop/seller/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seller)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Seller registered successfully"));
    }

    @Test
    public void testShowSellerForm() throws Exception {
        mockMvc.perform(get("/revshop/seller/form"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").doesNotExist()); // Assuming new Seller has no ID set
    }

    // Optional: Uncomment this test if you have implemented the method
    /*
    @Test
    public void testGetAllSellers() throws Exception {
        List<Seller> sellers = new ArrayList<>();
        sellers.add(new Seller(/* Set up Seller details ));

        when(sellerService.getAllSellers()).thenReturn(sellers);

        mockMvc.perform(get("/revshop/sellers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").hasSize(sellers.size()));
    } */
    
}
