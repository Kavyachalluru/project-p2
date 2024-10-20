package com.revshop.p2.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

       
        private Long buyerId;
        
        private Long productId;

        @Min(value = 1, message = "Quantity must be at least 1")
        private int quantity;

        private double price;  

        
        public Cart() {}

        public Cart(Long buyerId, Long product, int quantity) {
            this.buyerId = buyerId;
            this.productId = productId;
            this.quantity = quantity;
            //this.price = product.getDiscountPrice();  // Set price from the product's discount price
        }

        // Getters and Setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getBuyer() {
            return buyerId;
        }

        public void setBuyer(Long buyerId) {
            this.buyerId = buyerId;
        }

        public Long getProduct() {
            return productId;
        }

        public void setProduct(Long productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        // Calculate total price for this cart item
        public double getTotalPrice() {
            return this.price * this.quantity;
        }
    }