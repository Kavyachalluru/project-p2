package com.revshop.p2.Favourite_Services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle all generic exceptions
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception ex, WebRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        
        // Add exception details to the model
        model.addAttribute("errorMessage", "An error occurred: " + ex.getMessage());
        
        // Set the view name for the error page
        modelAndView.setViewName("error"); // This will render error.html page
        
        return modelAndView;
    }
}
