package com.revshop.client_app.model;


import java.util.List;

public class Orders {
	  
	    private Long id;
	    
	 
	    private Long buyerId;
	    private double totalPrice;

	    private String shippingAddress;

	    private String paymentMethod;

	    // Optional, only applicable if UPI is chosen
	    private List<OrderItems> orderItems;
	    
	    // Constructors, getters, setters
	    public Orders() {}

	    
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
