package com.revshop.p1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class P1Application {

	public static void main(String[] args) {
		SpringApplication.run(P1Application.class, args);
	}
	
}