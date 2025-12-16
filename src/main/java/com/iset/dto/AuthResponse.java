package com.iset.dto;

public class AuthResponse {
    private String token;
    private Long userId;
    private String email;
    private String username;   // ✅ NEW

    public AuthResponse() {
    }


    public AuthResponse(String token, Long userId, String email, String username) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.username = username;
    }

    // getters / setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {          // ✅ NEW
        return username;
    }

    public void setUsername(String username) {  // ✅ NEW
        this.username = username;
    }
}
