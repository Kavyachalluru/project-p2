//package com.example.p2.Favourite_Services.service;
//
//import com.example.p2.Favourite_Services.entity.Favourite;
//import com.example.p2.Favourite_Services.repository.FavouriteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class FavouriteService{
//
//    @Autowired
//    private FavouriteRepository favouriteRepository;
//
//    // Get favorites by buyerId
//    public List<Favourite> getFavouritesByBuyer(Long buyerId) {
//        return favouriteRepository.findByBuyerId(buyerId);
//    }
//
//    // Add a new favorite
//    public void addFavourite(Long buyerId, Long productId) {
//        // Simulate adding favorite without external service call
//        Favourite favourite = new Favourite(buyerId, productId);
//        favouriteRepository.save(favourite);
//    }
//
//    // Remove a favorite by its ID
//    public void removeFavourite(Long favouriteId) {
//        favouriteRepository.deleteById(favouriteId);
//    }
//    
// // New method to get a favourite by ID
//    public Optional<Favourite> getFavouriteById(Long favouriteId) {
//        return favouriteRepository.findById(favouriteId);
//    }
//}

package com.example.p2.Favourite_Services.service;

import com.example.p2.Favourite_Services.entity.Favourite;
import com.example.p2.Favourite_Services.repository.FavouriteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService{
	
	private static final Logger logger = LoggerFactory.getLogger(FavouriteService.class);

    @Autowired
    private FavouriteRepository favouriteRepository;

    // Get favorites by buyerId
    public List<Favourite> getFavouritesByBuyer(Long buyerId) {
    	logger.info("Fetching favourites for buyerId: {}", buyerId);
       // return favouriteRepository.findByBuyerId(buyerId);
    	 List<Favourite> favourites = favouriteRepository.findByBuyerId(buyerId);
         logger.debug("Number of favourites found for buyerId {}: {}", buyerId, favourites.size());
         return favourites;
    }

    // Add a new favorite
    public void addFavourite(Long buyerId, Long productId) {
    	 logger.info("Adding favourite - BuyerId: {}, ProductId: {}", buyerId, productId);
        // Simulate adding favorite without external service call
        Favourite favourite = new Favourite(buyerId, productId);
        favouriteRepository.save(favourite);
        logger.debug("Favourite saved for BuyerId: {} with ProductId: {}", buyerId, productId);
    }

    // Remove a favorite by its ID
    public void removeFavourite(Long favouriteId) {
    	 logger.info("Removing favourite with ID: {}", favouriteId);
        favouriteRepository.deleteById(favouriteId);
        logger.debug("Favourite removed with ID: {}", favouriteId);
    }
    
 // New method to get a favourite by ID
    public Optional<Favourite> getFavouriteById(Long favouriteId) {
    	logger.info("Fetching favourite with ID: {}", favouriteId);
    	Optional<Favourite> favourite = favouriteRepository.findById(favouriteId);
        if (favourite.isPresent()) {
            logger.debug("Favourite found with ID: {}", favouriteId);
        } else {
            logger.warn("Favourite not found with ID: {}", favouriteId);
        }
        return favourite;
    }
}





//package com.example.p2.Favourite_Services.service;
//
//import com.example.p2.Favourite_Services.entity.Favourite;
//import com.example.p2.Favourite_Services.repository.FavouriteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class FavouriteService {
//
//    @Autowired
//    private FavouriteRepository favouriteRepository;
//
//    @Autowired
//    private RestTemplate restTemplate;  // For communicating with user-service
//
//    // Get all favourites by buyerId
//    public List<Favourite> getFavouritesByBuyer(Long buyerId) {
//        return favouriteRepository.findByBuyerId(buyerId);
//    }
//
//    // Add a new favourite
//    public void addFavourite(Long buyerId, Long productId) throws Exception {
//        // Checks if buyer exists in user-service
//        String userServiceUrl = "http://localhost:8081/revshop/buyer/" + buyerId;
//        Boolean buyerExists = restTemplate.getForObject(userServiceUrl, Boolean.class);
//        if (buyerExists == null || !buyerExists) {
//            throw new Exception("Buyer not found");
//        }
//
//        // Assuming product verification is done similarly in product-service
//        String productServiceUrl = "http://localhost:8081/revshop/displayProducts/" + productId;
//        Boolean productExists = restTemplate.getForObject(productServiceUrl, Boolean.class);
//        if (productExists == null || !productExists) {
//            throw new Exception("Product not found");
//        }
//
//        // Save the favourite
//        Favourite favourite = new Favourite(buyerId, productId);
//        favouriteRepository.save(favourite);
//    }
//
//    // Get favourite by its ID
//    public Optional<Favourite> getFavouriteById(Long favouriteId) {
//        return favouriteRepository.findById(favouriteId);
//    }
//
//    // Remove a favourite by its ID
//    public void removeFavourite(Long favouriteId) {
//        favouriteRepository.deleteById(favouriteId);
//    }
//}




