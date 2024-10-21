package com.revshop.p2.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

       
        private Long buyer_id;
        //@OneToMany
        private Long product_id;

        @Min(value = 1, message = "Quantity must be at least 1")
        private int quantity;

        private double price;  
        
		

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getBuyer_id() {
			return buyer_id;
		}

		public void setBuyer_id(Long buyer_id) {
			this.buyer_id = buyer_id;
		}

		public Long getProduct_id() {
			return product_id;
		}

		public void setProduct_id(Long product_id) {
			this.product_id = product_id;
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

		public Cart() {
			super();
			this.id = id;
			this.buyer_id = buyer_id;
			this.product_id = product_id;
			this.quantity = quantity;
			this.price = price;
		}

		@Override
		public String toString() {
			return "Cart [id=" + id + ", buyer_id=" + buyer_id + ", product_id=" + product_id + ", quantity=" + quantity
					+ ", price=" + price + "]";
		}

		
		
    }