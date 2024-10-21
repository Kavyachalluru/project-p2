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
import com.revshop.userservice.controller.ProductController;
import com.revshop.userservice.entity.Product;
import com.revshop.userservice.entity.Seller;
import com.revshop.userservice.service.ProductService;
import com.revshop.userservice.service.SellerService;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private SellerService sellerService;

    @InjectMocks
    private ProductController productController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testAddProduct() throws Exception {
        Seller seller = new Seller();
        seller.setId(1L);
        Product product = new Product();
        product.setName("Product 1");

        // Simulate successful addition
        when(sellerService.getSellerById(1L)).thenReturn(seller);
        doNothing().when(productService).addProduct(any(Product.class)); // Mocking void method

        mockMvc.perform(post("/revshop/addProduct/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Product added successfully."));
    }

    @Test
    public void testGetProductsForSeller() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.findBySellerId(1L)).thenReturn(products);

        mockMvc.perform(get("/revshop/show/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(products.size()));
    }

    @Test
    public void testShowUpdateFormProductFound() throws Exception {
        Product product = new Product();
        product.setId(1L);
        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/revshop/product/update?productId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testShowUpdateFormProductNotFound() throws Exception {
        when(productService.getProductById(1L)).thenReturn(null);

        mockMvc.perform(get("/revshop/product/update?productId=1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found."));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Seller seller = new Seller();
        seller.setId(1L);
        Product product = new Product();
        product.setId(1L);

        when(sellerService.getSellerById(1L)).thenReturn(seller);
        doNothing().when(productService).updateProduct(any(Product.class)); // Mocking void method

        mockMvc.perform(post("/revshop/product/update?sellerId=1&productId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Seller seller = new Seller();
        seller.setId(1L);
        Product product = new Product();
        product.setId(1L);

        when(productService.getProductByIdAndSeller(1L, 1L)).thenReturn(product);
        doNothing().when(productService).deleteProductById(1L); // Mocking void method

        mockMvc.perform(get("/revshop/product/delete/1?sellerId=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchProductsByCategory() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.getProductsByCategory("Electronics")).thenReturn(products);

        mockMvc.perform(get("/revshop/search?category=Electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(products.size()));
    }

    @Test
    public void testSearchProductsByNameOrBrand() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.getProductsByNameOrBrand("Product")).thenReturn(products);

        mockMvc.perform(get("/revshop/searchByNameOrBrand?query=Product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(products.size()));
    }

    @Test
    public void testFilterProductsByDiscountPrice() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.getProductsByDiscountPriceRange(100.0, 500.0)).thenReturn(products);

        mockMvc.perform(get("/revshop/filterByDiscountPrice?mindiscountPrice=100&maxdiscountPrice=500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(products.size()));
    }
}