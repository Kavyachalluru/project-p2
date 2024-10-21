package com.revshop.client_app.dto;

import com.revshop.client_app.model.Favourite;
import com.revshop.client_app.model.Product;

public class FavouriteProductDTO {

    private Favourite favourite;
    private Product product;

    public FavouriteProductDTO(Favourite favourite, Product product) {
        this.favourite = favourite;
        this.product = product;
    }

    public Favourite getFavourite() {
        return favourite;
    }

    public Product getProduct() {
        return product;
    }

    // Add setters if needed
}
