package com.revshop.client_app.model;


public class OrderItems {
	   
	    private Long id;

	  	private Orders order;

	    private Long productId;

	    private int quantity;

	    private double unitPrice;

	    private double totalPrice;


	    public Long getId() {
	        return id;
	    }

	    public Orders getOrder() {
	        return order;
	    }

	    public void setOrder(Orders order) {
	        this.order = order;
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

	    public double getUnitPrice() {
	        return unitPrice;
	    }

	    public void setUnitPrice(double unitPrice) {
	        this.unitPrice = unitPrice;
	    }

	    public double getTotalPrice() {
	        return totalPrice;
	    }

	    public void setTotalPrice(double totalPrice) {
	        this.totalPrice = totalPrice;
	    }

	}

