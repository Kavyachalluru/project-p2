package com.example.p2.Favourite_Services.controller;

import com.example.p2.Favourite_Services.entity.Favourite;
import com.example.p2.Favourite_Services.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/favourites")
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    // Get all favourites by buyerId
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<Favourite>> getFavouritesByBuyer(@PathVariable Long buyerId) {
        System.out.println("FAV:::INIT");

        List<Favourite> favourites = favouriteService.getFavouritesByBuyer(buyerId);
        if (favourites.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(favourites);
        }
        System.out.println("FAV:::"+favourites.get(0).toString());
        return ResponseEntity.ok(favourites);
    }

    // Add a new favourite (buyerId and productId)
    @PostMapping("/add")
    public ResponseEntity<String> addFavourite(@RequestParam Long buyerId, @RequestParam Long productId) {
        try {
            favouriteService.addFavourite(buyerId, productId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Favourite added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add favourite.");
        }
    }

    // Remove a favourite by its ID
    @PostMapping("/remove/{favouriteId}")
    public ResponseEntity<String> removeFavourite(@PathVariable Long favouriteId) {
        Optional<Favourite> favourite = favouriteService.getFavouriteById(favouriteId);
        if (favourite.isPresent()) {
            favouriteService.removeFavourite(favouriteId);
            return ResponseEntity.ok("Favourite removed successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favourite not found.");
        }
    }
}






