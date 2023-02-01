package com.example.loginapp.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginapp.R;
import com.example.loginapp.model.LoginResponse;
import com.example.loginapp.network.LoginApi;

import java.io.IOException;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DataActivity extends AppCompatActivity {

    Button btnLogout;
    TextView tv_data;

    private Retrofit retrofit;
    private LoginApi apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        btnLogout=(Button)findViewById(R.id.logoutBtn);
        tv_data =(TextView)findViewById(R.id.tvUserData);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

      //  retrofit = new Retrofit.Builder()
        //        .baseUrl("https://api.example.com/")
          //      .addConverterFactory(GsonConverterFactory.create())
            //    .build();

        LoginResponse user = getUser();
    }

    private LoginResponse getUser() {
       LoginApi loginApi = retrofit.create(LoginApi.class);
        Call<LoginResponse> call = loginApi.getUser();

        try {
            Response<LoginResponse> response = call.execute();
            if (response.isSuccessful()) {
                LoginResponse loginResponse = response.body();
                tv_data.setText(loginResponse.toString());
                return loginResponse;
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
        } catch (IOException e) {
            // Handle network error
            tv_data.setText("Network Error");
        }

        return null;
    }

}