package com.revshop.client_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("revshop")
public class HomeController {
	
	@GetMapping("/home")
	public String displayHomePage() {
		System.out.println("entering");
		return "Home/Home";
	}
	@GetMapping("/about")
	public String displayAboutPage() {
		return "Home/about"; // Return the About page view
    
	}
}