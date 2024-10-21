package com.example.p2.Favourite_Services.entity;


public class Product {
	
		private Long id;
		private String name;
		private String brand;
		private double price;
		private double discountPrice;
		private String description;
		private String image_url;
		

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}
		

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}


		public double getDiscountPrice() {
			return discountPrice;
		}

		public void setDiscountPrice(double discountPrice) {
			this.discountPrice = discountPrice;
		}

		public String getImage_url() {
			return image_url;
		}

		public void setImage_url(String image_url) {
			this.image_url = image_url;
		}

}
