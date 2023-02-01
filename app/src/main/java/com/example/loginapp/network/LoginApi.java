package com.example.loginapp.network;

import com.example.loginapp.model.LoginRequest;
import com.example.loginapp.model.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginApi {

    @POST("/api/User/AdminLogin")
    Call<JWTResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("token")
    Call<ResponseBody> getToken(@Header("Authorization") String authToken);

    @GET("/api/User/User")
    Call<LoginResponse> getUser();

    /*

    @PUT("/api/User/Block")
    Call<Void> blockUser(@Query("userID") int userID);

    @PUT("/api/User/Unblock")
    Call<Void> unblockUser(@Query("userID") int userID);
    */

}

