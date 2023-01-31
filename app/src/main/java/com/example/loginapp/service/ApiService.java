package com.example.loginapp.service;

import com.example.loginapp.model.Data;
import com.example.loginapp.model.Login;
import com.example.loginapp.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    static Call<User> login(@Body Login login) {
        return null;
    }


    @GET("token")
    Call<ResponseBody> getToken(@Header("Authorization") String authToken);

    @POST("Data")
    static Call<Data> getData();

}
