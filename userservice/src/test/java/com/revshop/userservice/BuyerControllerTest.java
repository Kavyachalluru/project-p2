package com.revshop.userservice;




import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revshop.userservice.controller.BuyerController;
import com.revshop.userservice.entity.Buyer;
import com.revshop.userservice.service.BuyerService;
import jakarta.servlet.http.HttpSession;

@SpringBootTest
@AutoConfigureMockMvc
public class BuyerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyerService buyerService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private BuyerController buyerController;
    
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(buyerController).build();
    }

    @Test
    public void testRegisterBuyer() throws Exception {
        Buyer buyer = new Buyer(); // Set up buyer details
        when(buyerService.registerUser(any(Buyer.class))).thenReturn(buyer);

        mockMvc.perform(post("/revshop/buyerRegister")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buyer)))
                .andExpect(status().isCreated());
    }
    @Test
    public void testLoginBuyer() throws Exception {
        Buyer buyer = new Buyer();
        buyer.setFirstName("John");
        buyer.setLastName("Doe");

        // Mock session behavior
        doNothing().when(session).setAttribute(eq("loggedInUser"), any(Buyer.class));

        mockMvc.perform(post("/revshop/buyerLogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Buyer logged in successfully"));
    }

    @Test
    public void testUpdateBuyerProfile() throws Exception {
        Buyer buyer = new Buyer(); // Set up buyer details
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", buyer);

        mockMvc.perform(put("/revshop/buyer/update")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buyer)))
                .andExpect(status().isOk());
    }

    @Test
    public void testShowUpdateFormLoggedOut() throws Exception {
        // Simulate no user logged in
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        mockMvc.perform(get("/revshop/buyer/update"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Buyer not logged in"));
    }
}

