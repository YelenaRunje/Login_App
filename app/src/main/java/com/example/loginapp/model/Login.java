package com.example.loginapp.model;

public class Login {

    private String email;
    private String password;
    private String appId;

    public Login(String email, String password, String appId){
        this.email = email;
        this.password = password;
        this.appId = appId;
    }
}
