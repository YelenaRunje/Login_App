package com.example.loginapp.ui;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginapp.R;
import com.example.loginapp.model.LoginResponse;
import com.example.loginapp.network.LoginApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DataActivity extends AppCompatActivity {

    Button btnLogout;
    TextView tv_data;

    private SharedPreferences sharedPreferences;


    private Retrofit retrofit;
    private LoginApi loginApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        btnLogout=(Button)findViewById(R.id.logoutBtn);
        tv_data =(TextView)findViewById(R.id.tvUserData);

        sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);

        getUser();

        btnLogout.setOnClickListener(view -> {
            logout(); // implemented in logout method : start login activity, delete token
        });


    }

    // this time without using instance of RetrofitClient.Class

    private void getUser() {
        Call<LoginResponse> call = loginApi.getUser();

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    tv_data.setText(loginResponse.toString());
                } else {
                    // Handle HTTP error codes
                    if (response.code() == 400) {
                        // 400 - bad request
                        tv_data.setText("Bad Request");
                    } else if (response.code() == 401) {
                        // 401 - not authorized
                        tv_data.setText("Unauthorized");
                    } else if (response.code() == 404) {
                        // 404 - not found
                        tv_data.setText("Not Found");
                    } else {
                        // generic error
                        tv_data.setText("Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                tv_data.setText("Network Error");
            }
        });
    }


    public void logout() {
        Call<ResponseBody> call = loginApi.userLogout();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    deleteToken();
                    startLoginActivity();
                } else {
                    Toast.makeText(DataActivity.this, "Error logging out", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Error logging out", t);
                Toast.makeText(DataActivity.this, "Error logging out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}