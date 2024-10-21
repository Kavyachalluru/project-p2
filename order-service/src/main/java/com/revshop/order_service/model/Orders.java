package com.revshop.order_service.model;


import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@Entity
@Table(name = "orders")

@JsonIgnoreProperties(ignoreUnknown = true)
public class Orders {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @Column(name = "buyer_id")
	    private Long buyerId;
	    
	    private double totalPrice;

	    private String shippingAddress;

	    private String paymentMethod;

	    // Optional, only applicable if UPI is chosen
	    @JsonManagedReference
	    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	    private List<OrderItems> orderItems;
	        
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


		public double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(double totalPrice) {
			this.totalPrice = totalPrice;
		}

		public String getShippingAddress() {
			return shippingAddress;
		}

		public void setShippingAddress(String shippingAddress) {
			this.shippingAddress = shippingAddress;
		}

		public String getPaymentMethod() {
			return paymentMethod;
		}

		public void setPaymentMethod(String paymentMethod) {
			this.paymentMethod = paymentMethod;
		}

		
		public List<OrderItems> getOrderItems() {
			return orderItems;
		}

		public void setOrderItems(List<OrderItems> orderItems) {
			this.orderItems = orderItems;
		}
	    

}