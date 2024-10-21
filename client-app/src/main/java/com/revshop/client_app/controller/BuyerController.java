package com.revshop.client_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.revshop.client_app.model.Buyer;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/revshop")
public class BuyerController {

    @Autowired
    private RestTemplate restTemplate;

    private final String USER_SERVICE_URL = "http://localhost:8081/revshop"; 

    // Show Buyer Registration Form
    @GetMapping("/buyerRegister")
    public String showBuyerRegForm(Model model) {
        model.addAttribute("buyer", new Buyer());
        return "buyerReg";
    }

    // Handle Buyer Registration
    @PostMapping("/buyerRegister")
    public String registerBuyer(Model model, @ModelAttribute("buyer") Buyer buyer) {
        String buyerServiceUrl = USER_SERVICE_URL + "/buyerRegister";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(buyerServiceUrl, buyer, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                return "redirect:/revshop/login";
            } else {
                model.addAttribute("error", "Error registering buyer. Please try again.");
                return "buyerReg";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Service is currently unavailable. Please try again later.");
            return "buyerReg";
        }
    }

    // Show Update Form for Buyer
    @GetMapping("/buyer/update")
    public String showUpdateForm(HttpSession session, Model model) {
        Buyer buyer = (Buyer) session.getAttribute("loggedInUser");
        if (buyer == null) {
            return "redirect:/revshop/login"; // Redirect to login if not logged in
        }

        // Fetch current buyer details from user service by ID
        ResponseEntity<Buyer> response = restTemplate.getForEntity(USER_SERVICE_URL + "/buyer/" + buyer.getBuyer_id(), Buyer.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("buyer", response.getBody());
            return "updateForm"; // Renders updateForm.html
        } else {
            model.addAttribute("error", "Unable to load buyer information.");
            return "error"; // Redirect to an error page
        }
    }

    // Handle Buyer Update
    @PostMapping("/buyer/update")
    public String updateBuyer(HttpSession session, @ModelAttribute("buyer") Buyer updatedBuyer, Model model) {
        Buyer loggedInBuyer = (Buyer) session.getAttribute("loggedInUser");
        System.out.println("its getting printed........#######################################...........");

        if (loggedInBuyer == null) {
            return "redirect:/revshop/login"; // Redirect if not logged in
        }

        // Send updated buyer information to user service via RestTemplate
        ResponseEntity<String> response = restTemplate.exchange(
                USER_SERVICE_URL + "/buyer/update",
                HttpMethod.PUT,
                new HttpEntity<>(updatedBuyer),
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            // Update session with the new buyer information
            session.setAttribute("loggedInUser", updatedBuyer);
            model.addAttribute("message", "Profile updated successfully.");
            return "updateForm"; // Show the update form with success message
        } else {
            model.addAttribute("error", "Failed to update profile.");
            return "updateForm"; // Stay on the form with an error
        }
    }
}



//package com.revshop.client_app.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.client.RestTemplate;
//
//import com.revshop.client_app.model.Buyer;
//
//@Controller
//@RequestMapping("/revshop")
//public class BuyerController {
//
//  @Autowired
//  private RestTemplate restTemplate;
//
//  @GetMapping("/buyerRegister")
//  public String showBuyerRegForm(Model model) {
//      model.addAttribute("buyer", new Buyer()); 
//      return "buyerReg"; 
//  }
//
//  @PostMapping("/buyerRegister")
//  public String registerBuyer(Model model, @ModelAttribute("buyer") Buyer buyer) {
//      String buyerServiceUrl = "http://localhost:8081/revshop/buyerRegister"; 
//
//      try {
//          ResponseEntity<String> response = restTemplate.postForEntity(buyerServiceUrl, buyer, String.class);
//
//          if (response.getStatusCode() == HttpStatus.CREATED) {
//              return "redirect:/revshop/login";
//          } else {
//              model.addAttribute("error", "Error registering buyer. Please try again.");
//              return "buyerReg"; 
//          }
//      } catch (Exception e) {
//          e.printStackTrace();
//          model.addAttribute("error", "Service is currently unavailable. Please try again later.");
//          return "buyerReg"; 
//      }
//  }
//}


