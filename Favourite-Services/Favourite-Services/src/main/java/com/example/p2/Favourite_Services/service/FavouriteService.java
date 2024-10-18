package com.example.p2.Favourite_Services.service;

import com.example.p2.Favourite_Services.entity.Favourite;
import com.example.p2.Favourite_Services.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService{

    @Autowired
    private FavouriteRepository favouriteRepository;

    // Get favorites by buyerId
    public List<Favourite> getFavouritesByBuyer(Long buyerId) {
        return favouriteRepository.findByBuyerId(buyerId);
    }

    // Add a new favorite
    public void addFavourite(Long buyerId, Long productId) {
        // Simulate adding favorite without external service call
        Favourite favourite = new Favourite(buyerId, productId);
        favouriteRepository.save(favourite);
    }

    // Remove a favorite by its ID
    public void removeFavourite(Long favouriteId) {
        favouriteRepository.deleteById(favouriteId);
    }
    
 // New method to get a favourite by ID
    public Optional<Favourite> getFavouriteById(Long favouriteId) {
        return favouriteRepository.findById(favouriteId);
    }
}

//
//package com.example.p2.Favourite_Services.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import com.example.p2.Favourite_Services.entity.Favourite;
//import com.example.p2.Favourite_Services.repository.FavouriteRepository;
//
//import java.util.List;
//
//@Service
//public class FavouriteService {
//
//    @Autowired
//    private FavouriteRepository favouriteRepository;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    // URLs for Buyer and Product microservices
//    private static final String BUYER_SERVICE_URL = "http://localhost:8081/buyers/";  // Buyer service URL
//    private static final String PRODUCT_SERVICE_URL = "http://localhost:8082/products/";  // Product service URL
//
//    // Add a favourite using buyerId and productId
//    public void addFavourite(Long buyerId, Long productId) {
//        // Fetch buyer and product details using RestTemplate
//        String buyerUrl = BUYER_SERVICE_URL + buyerId;
//        String productUrl = PRODUCT_SERVICE_URL + productId;
//
//        // You can also add error handling in case the buyer or product is not found
//        Object buyer = restTemplate.getForObject(buyerUrl, Object.class);
//        Object product = restTemplate.getForObject(productUrl, Object.class);
//
//        // If both buyer and product exist, proceed with adding the favourite
//        if (buyer != null && product != null) {
//            Favourite existingFavourite = favouriteRepository.findByBuyerIdAndProductId(buyerId, productId);
//            if (existingFavourite == null) {
//                Favourite favourite = new Favourite(buyerId, productId);
//                favouriteRepository.save(favourite);
//            }
//        } else {
//            throw new RuntimeException("Buyer or Product not found");
//        }
//    }
//
//    // Get all favourites for a specific buyer using buyerId
//    public List<Favourite> getFavouritesByBuyer(Long buyerId) {
//        return favouriteRepository.findByBuyerId(buyerId);
//    }
//
//    // Remove a favourite by its ID
//    public void removeFavourite(Long favouriteId) {
//        favouriteRepository.deleteById(favouriteId);
//    }
//}




