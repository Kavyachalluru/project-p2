package com.example.p2.Favourite_Services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.p2.Favourite_Services.entity.Favourite;
import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    // Find all favourites by buyerId
    List<Favourite> findByBuyerId(Long buyerId);

    // Find a specific favourite by buyerId and productId
    Favourite findByBuyerIdAndProductId(Long buyerId, Long productId);
}
