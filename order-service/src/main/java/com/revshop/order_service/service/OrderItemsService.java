package com.revshop.order_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revshop.order_service.model.OrderItems;
import com.revshop.order_service.model.Product;
import com.revshop.order_service.repository.OrderItemsRepository;


@Service
public class OrderItemsService {
	@Autowired
	OrderItemsRepository orderitemrepo;
	@Autowired
	OrderService os;
	
	
	@Autowired
    private RestTemplate restTemplate;

    private final String PRODUCT_SERVICE_URL = "http://localhost:8081/api/products"; // Adjust URL

    // Method to fetch Product by ID
    public Product getProductById(Long productId) {
        String url = PRODUCT_SERVICE_URL + "/" + productId;
        return restTemplate.getForObject(url, Product.class);
    }
	
	public void saveorderitem(OrderItems orderitem) {
		orderitemrepo.save(orderitem);
	}


}
