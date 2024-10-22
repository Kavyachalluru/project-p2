package com.revshop.userservice.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.userservice.Repository.ProductRepository;
import com.revshop.userservice.entity.Product;



@Service
public class ProductService {
	
	 private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private ProductRepository product_repo;
	
	// Add a new product
    public void addProduct(Product product) {
        product_repo.save(product);
        logger.info("Added new product with name: {}", product.getName());
    }
    
    // Showing products based on sellerId
    public List<Product> getAllProducts(Long id){
    	logger.info("Fetching all products for sellerId: {}", id);
    	return product_repo.findBySellerId(id);
    }
    
    // Get a product by ID and Seller ID (for authorization)
    public Product getProductByIdAndSeller(Long productId, Long sellerId) {
    	
        Optional<Product> optionalProduct = product_repo.findByIdAndSellerId(productId, sellerId);
        if (optionalProduct.isPresent()) {
            logger.info("Product found: {}", optionalProduct.get().getName());
        } else {
            logger.warn("Product not found for productId: {} and sellerId: {}", productId, sellerId);
        }
        return optionalProduct.orElse(null);
    }

    // Update the product
    public void updateProduct(Product product) {
    	product_repo.save(product); // Save the updated product
    	logger.info("Updated product with id: {}", product.getId());
    }
    
    // Delete a product by ID
    public void deleteProductById(Long productId) {
    	product_repo.deleteById(productId);
    	logger.info("Deleted product with id: {}", productId);
    	
    }
    public List<Product> getAllProducts(){
    	logger.info("Fetching all products");
    	return product_repo.findAll();
    }
    public Product getProductById(Long id) {
        return product_repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
	
    // search by category
    public List<Product> getProductsByCategory(String category) {
    	logger.info("Fetching products by category: {}", category);
        return product_repo.findByCategory(category);
    }
    
  //search products by name
    public List<Product> getProductsByNameOrBrand(String query) {
    	logger.info("Searching products by name or brand with query: {}", query);
        return product_repo.findByNameContainingOrBrandContainingIgnoreCase(query,query); 
    }

    //filter by price
    public List<Product> getProductsByDiscountPriceRange(double mindiscountPrice, double maxdiscountPrice){
    	logger.info("Fetching products with discount price between {} and {}", mindiscountPrice, maxdiscountPrice);
    	return product_repo.findByDiscountPriceBetween(mindiscountPrice, maxdiscountPrice);
    	
    }

	public List<Product> findBySellerId(Long id) {
		logger.info("Fetching products for sellerId: {}", id);
		return product_repo.findBySellerId(id);
	}

}
