package com.example.loginapp.model;

public class LoginRequest {
    private String appId;
    private String email;
    private String password;

    public LoginRequest(String appId, String email, String password) {
        this.appId = appId;
        this.email = email;
        this.password = password;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

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
}