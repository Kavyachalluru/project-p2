package com.example.p2.Favourite_Services.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="favourites")
public class Favourite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long buyerId;  // ID from Buyer microservice
    private Long productId;  // ID from Product microservice
    @Transient
    private Product product;
    
    

    public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Favourite() {
    }

    public Favourite(Long buyerId, Long productId) {
        this.buyerId = buyerId;
        this.productId = productId;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

	@Override
	public String toString() {
		return "Favourite [id=" + id + ", buyerId=" + buyerId + ", productId=" + productId + ", product=" + product
				+ "]";
	}
    
    
}
