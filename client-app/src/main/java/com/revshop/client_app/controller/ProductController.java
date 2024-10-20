package com.revshop.client_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.revshop.client_app.model.Product;
import com.revshop.client_app.model.Seller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/revshop")
@SessionAttributes("loggedInUser")
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PRODUCT_SERVICE_URL = "http://localhost:8081/revshop"; // Update with actual product service URL

    @GetMapping("/product")
    public String showProductForm(Model model,HttpSession session ) {
    	Long sellerId = (Long) session.getAttribute("loggedInUser");
        if (sellerId == null) {
            return "redirect:/revshop/login"; // Redirect if not logged in
        }

        model.addAttribute("seller", sellerId);
        model.addAttribute("product", new Product());
        return "product_form"; 
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute Product product, HttpServletRequest request, HttpSession session) {
        Long sellerId = (Long) session.getAttribute("loggedInUser");
        if (sellerId == null) {
            return "redirect:/revshop/login";
        }

        String productServiceUrl = UriComponentsBuilder.fromHttpUrl(PRODUCT_SERVICE_URL)
                                .path("/addProduct/{sellerId}")
                                .buildAndExpand(sellerId)
                                .toUriString();
        System.out.println(productServiceUrl);

        try {
            restTemplate.postForObject(productServiceUrl, product, String.class);
        } catch (HttpClientErrorException e) {
            System.out.println("Error adding product: " + e.getMessage());
            return "error"; 
        }

        return "redirect:/revshop/show";
    }


    @GetMapping("/show")
    public String displayProductsForSeller(Model model, HttpSession session) {
        Long sellerId = (Long) session.getAttribute("loggedInUser");

        if (sellerId == null) {
            return "redirect:/revshop/login";
        }

        try {
            Product[] products = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/show/" + sellerId, Product[].class);
            model.addAttribute("products", List.of(products));
        } catch (HttpClientErrorException e) {
            
            System.err.println("Error fetching products: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            model.addAttribute("message", "Error fetching products: " + e.getMessage());
        } catch (Exception e) {
            model.addAttribute("message", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        return "SellerDashBoard";
    }

    @GetMapping("/product/update")
    public String showUpdateForm(@RequestParam("productId") Long id, Model model, HttpSession session) {
    	 Long sellerId = (Long) session.getAttribute("loggedInUser");
        if (sellerId == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }
        
        Product product = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/product/{productId}", Product.class, id);
        if (product != null) {
            model.addAttribute("product",product);
            return "UpdateProductBySeller"; 
        }
        return "error/403"; 
    }
    
    @PostMapping("/product/update")
    public String updateProduct(@ModelAttribute Product product,@RequestParam("productId") Long productId, HttpSession session) {
        Long sellerId = (Long) session.getAttribute("loggedInUser"); 
        System.out.println("SellerId from session updatew: " + sellerId);
        System.out.println("product id = "+productId);

        if (sellerId != null && productId != null) {
        	System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            try {
                restTemplate.postForObject(PRODUCT_SERVICE_URL + "/product/update?sellerId=" + sellerId + "&productId=" + productId, product, Product.class);
                return "redirect:/revshop/show"; 
            } catch (Exception e) {
                e.printStackTrace(); 
                return "error/500"; 
            }
        }
        return "redirect:/revshop/login"; 
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, HttpSession session) {
    	Long sellerId = (Long) session.getAttribute("loggedInUser"); 

    	restTemplate.getForObject(PRODUCT_SERVICE_URL + "/product/delete/" + id + "?sellerId=" + sellerId, Void.class);

        
        return "redirect:/revshop/show";
    }

    @GetMapping("/displayProducts")
    public String showProducts(Model model, HttpSession session) {    	
    	Product[] products = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/displayProducts", Product[].class);
        
        model.addAttribute("products", List.of(products));
        return "showProducts";  
    }

    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable("id") Long id, Model model) {
        Product product = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/products/" + id, Product.class);
        model.addAttribute("product", product);
        return "productDetails";  // This view shows individual product details
    }

    @GetMapping("/search")
    public String searchProductsByCategoryForBuyer(@RequestParam("category") String category, Model model) {
        Product[] products;

        // If no category is selected, show all products
        if (category == null || category.isEmpty()) {
            products = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/products", Product[].class); // Fetch all products
        } else {
            // Otherwise, filter products by category
            products = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/products/category/" + category, Product[].class);
        }

        model.addAttribute("products", List.of(products));
        return "showProducts"; // Return buyer dashboard template with products
    }

    @GetMapping("/searchByNameOrBrand")
    public String searchProductsByNameorBrand(@RequestParam(value ="query", required = false) String query, Model model) {
        Product[] products;

        if (query == null || query.isEmpty()) {
            products = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/products", Product[].class); // Fetch all products if no search term
        } else {
            // Search products by name using the service method
            products = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/products/search?query=" + query, Product[].class);
        }

        model.addAttribute("products", List.of(products));
        return "showProducts"; // Return the view with the filtered products
    }

    @GetMapping("/filterByDiscountPrice")
    public String filterProductsByDiscountPrice(@RequestParam("mindiscountPrice") double mindiscountPrice, @RequestParam("maxdiscountPrice") double maxdiscountPrice, Model model) {
        Product[] products = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/products/filter?minDiscountPrice=" + mindiscountPrice + "&maxDiscountPrice=" + maxdiscountPrice, Product[].class);
        model.addAttribute("products", List.of(products));
        return "showProducts";
    }
}
