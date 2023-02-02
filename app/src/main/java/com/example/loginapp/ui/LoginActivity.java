package com.example.loginapp.ui;

import static android.widget.Toast.makeText;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginapp.R;
import com.example.loginapp.network.RetrofitClient;
import com.example.loginapp.model.LoginRequest;
import com.example.loginapp.network.JWTResponse;
import com.example.loginapp.network.LoginApi;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private String email, password, appId;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appId = "f5a14a7f-6460-4b28-8e7c-e3b95725689d";
        sharedPreferences = getSharedPreferences("TokenPreferences", Context.MODE_PRIVATE);

        Button btnLogin = findViewById(R.id.loginBtn);
        TextInputLayout emailInput = findViewById(R.id.emailInput);
        TextInputLayout passwordInput = findViewById(R.id.passwordInput);

        btnLogin.setOnClickListener(view -> {
            email = Objects.requireNonNull(emailInput.getEditText()).getText().toString();
            password = Objects.requireNonNull(passwordInput.getEditText()).getText().toString();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                makeText(LoginActivity.this, "Username / Password Required", Toast.LENGTH_LONG).show();
            } else {
                // proceed to login
                login();
            }
        });
    }

    private void login() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in, please wait...");
        progressDialog.show();

        LoginApi loginService = RetrofitClient.getRetrofitInstance().create(LoginApi.class);
        Call<JWTResponse> call = loginService.userLogin(new LoginRequest(appId, email, password));
        call.enqueue(new Callback<JWTResponse>() {
            @Override
            public void onResponse(@NonNull Call<JWTResponse> call, @NonNull Response<JWTResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    saveToken(response.body().getToken());
                    startDataActivity();
                } else {
                    makeText(LoginActivity.this, "Error logging in", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JWTResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "Error logging in", t);
                makeText(LoginActivity.this, "Error logging in", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startDataActivity() {
        Intent intent = new Intent(this, DataActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
    }
}
