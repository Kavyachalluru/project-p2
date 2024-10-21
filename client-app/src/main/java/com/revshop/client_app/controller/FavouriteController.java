package com.revshop.client_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.revshop.client_app.model.Favourite;
import com.revshop.client_app.model.Product;

import jakarta.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/revshop")
public class FavouriteController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String FAVOURITE_SERVICE_URL = "http://localhost:8090/api/favourites"; 
    private static final String USER_SERVICE_URL = "http://localhost:8081/revshop";
    
    // Get all favourites by buyerId
    @GetMapping("/favourites/buyer")
    public String getFavourites(HttpSession session, Model model) {
        System.out.println("Its printing");

        Long buyerId = (Long) session.getAttribute("loggedInUser"); // Retrieves buyerId from session
        if (buyerId == null) {
            return "redirect:/revshop/login"; // Redirect to login if not logged in
        }
        Favourite[] response = {};
        try {
         response = restTemplate.getForObject(FAVOURITE_SERVICE_URL+"/buyer/" + buyerId, Favourite[].class);
        System.out.println(response[0].getProductId());
        }catch(Exception ex) {
        	ex.printStackTrace();
        }
        if (response.length > 0) {
            List<Favourite> favourites = Arrays.asList(response);
            System.out.println(favourites.get(0).getProduct());
            
            System.out.println("FAV:::"+favourites.get(0).getProductId());
            for (Favourite favourite : favourites) {
            	Product product = restTemplate.getForObject(USER_SERVICE_URL + "/product/" + favourite.getProductId(), Product.class);
                System.out.println(product.getName());
                favourite.setProduct(product);
                // Should print the Product object
            }
            model.addAttribute("favourites", favourites);
           System.out.println(favourites.get(0).getProductId());
           
            }
        else {
            model.addAttribute("message", "No favourites found for this buyer.");
          
        }
        
        return "favourites"; 
    }

    // Add a new favourite (uses buyerId from session)  
    @PostMapping("/favourites/add")
    public String addFavourite( @RequestParam Long productId ,HttpSession session, Model model) {
        Long buyerId = (Long) session.getAttribute("loggedInUser"); // Retrieve buyerId from session
        if (buyerId == null) {
            return "redirect:/revshop/login"; // Redirect if not logged in
        }
//        Long productId =(Long)  model.;
        System.out.println("PRODUCT:::" + productId);
        System.out.println("PRODUCT:::" + buyerId);

        // checks if the product is already in favourites for this buyer
        Favourite[] response = {};
        try {
         response = restTemplate.getForObject(FAVOURITE_SERVICE_URL+"/buyer/" + buyerId, Favourite[].class);
        System.out.println("PRODUCT:::" + response[0].getId());
        }catch (Exception ex){	
        	ex.printStackTrace();
        }

        if (response.length > 0) {
            List<Favourite> favourites =  Arrays.asList(response);

            // Checks if the product is already in favourites
            boolean isAlreadyFavourite = favourites.stream()
                .anyMatch(favourite -> favourite.getProductId().equals(productId));

            if (isAlreadyFavourite) {
                model.addAttribute("error", "Product is already in your favourites.");
                return "redirect:/revshop/displayProducts"; // Redirect after duplicate check
            }
        }

        // Proceed with adding the product to favourites
        try {
            ResponseEntity<String> addResponse = restTemplate.postForEntity(
                FAVOURITE_SERVICE_URL + "/add?buyerId=" + buyerId + "&productId=" + productId, 
                null, 
                String.class
            );

            if (addResponse.getStatusCode() == HttpStatus.CREATED) {
                model.addAttribute("message", "Product added to favourites.");
            } else {
                model.addAttribute("error", "Failed to add favourite.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while adding favourite: " + e.getMessage());
        }

        return "redirect:/revshop/displayProducts"; // Redirect after adding favourite
    }


    // Remove a favourite by its ID (uses buyerId from session for confirmation if needed)
    @PostMapping("/favourites/remove/{favouriteId}")
    public String removeFavourite(@PathVariable Long favouriteId, HttpSession session, Model model) {
        Long buyerId = (Long) session.getAttribute("loggedInUser"); // Retrieve buyerId from session
        if (buyerId == null) {
            return "redirect:/revshop/login"; // Redirect if not logged in
        }

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                FAVOURITE_SERVICE_URL + "/remove/" + favouriteId,  
                HttpMethod.POST,
                null, 
                String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("message", response.getBody());
            } else {
                model.addAttribute("error", "Favourite not found.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while removing favourite: " + e.getMessage());
        } 
        return "redirect:/revshop/favourites/buyer";
    }
}