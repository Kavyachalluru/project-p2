package com.example.p2.Favourite_Services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FavouriteServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FavouriteServicesApplication.class, args);
	}

}
