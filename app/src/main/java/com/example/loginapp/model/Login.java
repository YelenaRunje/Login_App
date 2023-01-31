package com.example.loginapp.model;

public class Login {

    private String email;
    private String password;
    private String appId = "f5a14a7f-6460-4b28-8e7c-e3b95725689d";

    public Login(String email, String password, String appId){
        this.email = email;
        this.password = password;
        this.appId = appId;
    }

}
