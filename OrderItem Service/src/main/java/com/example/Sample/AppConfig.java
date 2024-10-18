package com.example.Sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // Define RestTemplate as a bean to be used across the application
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
