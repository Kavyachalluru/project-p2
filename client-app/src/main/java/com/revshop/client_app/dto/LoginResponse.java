package com.revshop.client_app.dto;

import com.revshop.client_app.model.User;

public class LoginResponse {
    private String userType;
    private User user; // Use the User interface here

    public LoginResponse(String userType, User user) {
        this.userType = userType;
        this.user = user;
    }

    // Getters and Setters

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
