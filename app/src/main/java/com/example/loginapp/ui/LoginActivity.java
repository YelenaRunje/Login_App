package com.example.loginapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginapp.network.JWTResponse;
import com.example.loginapp.network.ManageToken;
import com.example.loginapp.R;
import com.example.loginapp.model.LoginRequest;
import com.example.loginapp.network.LoginApi;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private String email, password, appId;
    private LoginApi loginApi;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appId = "f5a14a7f-6460-4b28-8e7c-e3b95725689d";
        Button btnLogin = findViewById(R.id.loginBtn);
        TextInputLayout email_input = findViewById(R.id.emailInput);
        TextInputLayout password_input = findViewById(R.id.passwordInput);

        ManageToken manageToken = new ManageToken(this);
        sharedPreferences = manageToken.getSharedPreferences();
        sharedPreferences.edit().putString("token", "your_token").apply();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://account.zadar.mediaapp.eu")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginApi = retrofit.create(LoginApi.class);

        btnLogin.setOnClickListener(view -> {
            email = Objects.requireNonNull(email_input.getEditText()).getText().toString();
            password = Objects.requireNonNull(password_input.getEditText()).getText().toString();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Username / Password Required", Toast.LENGTH_LONG).show();
            } else {
                // proceed to login
                login();
            }
        });
    }

    private void login() {
        Call<JWTResponse> call = loginApi.userLogin(new LoginRequest(email, password, appId));
        call.enqueue(new Callback<JWTResponse>() {
            @Override
            public void onResponse(Call<JWTResponse> call, Response<JWTResponse> response) {
                if (response.isSuccessful()) {
                    // Parse the response and get the JWT token
                    String token = parseToken(response.body().toString());
                    saveToken(token);
                    // starting new activity
                    startDataActivity();
                } else {
                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<JWTResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startDataActivity() {
        Intent intent = new Intent(this, DataActivity.class);
        startActivity(intent);
    }


    private String parseToken(String responseBody) {
        // parse the response body and extract the JWT token
        String token = "";
        try {
            JSONObject responseObject = new JSONObject(responseBody);
            token = responseObject.getString("token");
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "JSONException", Toast.LENGTH_SHORT).show();
        }
        return token;
    }



    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public void removeToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
    }


}