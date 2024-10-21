package com.revshop.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revshop.userservice.entity.Product;
import com.revshop.userservice.entity.Seller;
import com.revshop.userservice.service.ProductService;
import com.revshop.userservice.service.SellerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/revshop")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private SellerService ss;

    @GetMapping("/product")
    public ResponseEntity<String> showProductForm(Model model, HttpSession session) {
        model.addAttribute("product", new Product());
        return ResponseEntity.ok("product_form"); 
    }

    @PostMapping("/addProduct/{sellerId}")
    public ResponseEntity<String> addProduct(@RequestBody Product product, @PathVariable Long sellerId) {
    	
        if (sellerId == null) {
        	System.out.println("seller &&&&&&&&&%%%%%%%%");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not logged in.");
        }
        Seller seller=ss.getSellerById(sellerId);
        System.out.println("product adding");
        product.setSeller(seller); 
        productService.addProduct(product); 

        return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully.");
    }


    @GetMapping("/show/{id}")
    public ResponseEntity<List<Product>> getProductsForSeller(@PathVariable Long id) {
        System.out.println("Received request for seller ID: " + id);
        List<Product> products = productService.findBySellerId(id);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }


    @GetMapping("/product/update")
    public ResponseEntity<?> showUpdateForm(@RequestParam("productId") Long id ) {
        Product product = productService.getProductById(id);
        if (product != null) {
            
            return ResponseEntity.ok(product); // Render update form view
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
    }

    @PostMapping("/product/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @RequestParam("sellerId") Long sellerId, @RequestParam("productId") Long id) {
    	System.out.println("Received sellerId: " + sellerId); 
        System.out.println("Received productId: " + id); 

        if (sellerId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not logged in.");
        }

        product.setId(id); 
        Seller seller=ss.getSellerById(sellerId);
        product.setSeller(seller);
        try {
            productService.updateProduct(product);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product.");
        }
    }

    @GetMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id,@RequestParam Long sellerId) {
    	System.out.println("delete sellerId: " + sellerId);
        if (sellerId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not logged in.");
        }

        Product product = productService.getProductByIdAndSeller(id, sellerId);
        if (product != null) {
            productService.deleteProductById(id);
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
    }

    @GetMapping("/displayProducts")
    public ResponseEntity<List<Product>> showProducts(HttpSession session) {
    	
        List<Product> products = productService.getAllProducts();
        System.out.println("products response");
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        System.out.println("PRODUCT:::"+product.getSeller());

        if (product != null) {
            return ResponseEntity.ok(product);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByCategoryForBuyer(@RequestParam("category") String category) {
    	System.out.println("search controller...!");
        List<Product> products;

        if (category == null || category.isEmpty()) {
            products = productService.getAllProducts();
        } else {
            products = productService.getProductsByCategory(category);
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping("/searchByNameOrBrand")
    public ResponseEntity<List<Product>> searchProductsByNameOrBrand(@RequestParam(value = "query", required = false) String query) {
        List<Product> products;

        if (query == null || query.isEmpty()) {
            products = productService.getAllProducts();
        } else {
            products = productService.getProductsByNameOrBrand(query);
        }

        return ResponseEntity.ok(products);
    }

    // Filter by price
    @GetMapping("/filterByDiscountPrice")
    public ResponseEntity<List<Product>> filterProductsByDiscountPrice(@RequestParam("mindiscountPrice") double mindiscountPrice, @RequestParam("maxdiscountPrice") double maxdiscountPrice) {
        List<Product> products = productService.getProductsByDiscountPriceRange(mindiscountPrice, maxdiscountPrice);
        return ResponseEntity.ok(products);
    }
}
