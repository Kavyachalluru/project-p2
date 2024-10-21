package com.revshop.order_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.revshop.order_service.dto.BuyerDTO;

public class BuyerService {

	 @Autowired
	    private RestTemplate restTemplate;

	    public BuyerDTO getBuyerById(Long buyerId) {
	        String url = "http://localhost:8081/revshop/buyers/" + buyerId;
	        return restTemplate.getForObject(url, BuyerDTO.class);
	    }
}
