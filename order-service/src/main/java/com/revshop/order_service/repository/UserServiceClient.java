package com.revshop.order_service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revshop.order_service.model.Buyer;
import com.revshop.order_service.model.Product;

@Service
public class UserServiceClient {

    private final String USER_SERVICE_URL = "http://localhost:8081/revshop";

    @Autowired
    private RestTemplate restTemplate;

    public Buyer getBuyerById(Long id) {
        String url = USER_SERVICE_URL + "/buyers/" + id;
        return restTemplate.getForObject(url, Buyer.class);
    }

    public Product getProductById(Long id) {
        String url = USER_SERVICE_URL + "/product/" + id;
        return restTemplate.getForObject(url, Product.class);
    }
}
