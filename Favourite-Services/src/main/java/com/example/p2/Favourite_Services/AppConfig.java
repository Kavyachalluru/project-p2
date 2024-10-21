package com.example.p2.Favourite_Services;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
