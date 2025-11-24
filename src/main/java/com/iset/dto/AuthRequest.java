package com.iset.dto;

import jakarta.annotation.Nullable;

public class AuthRequest {

    private String email;
    private String password;

    // Optional fields for signup
    @Nullable
    private String username;
    @Nullable
    private String confirmPassword;

    public AuthRequest() {}

    // ---- GETTERS & SETTERS ----

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username; // may be null
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConfirmPassword() {
        return confirmPassword; // may be null
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
