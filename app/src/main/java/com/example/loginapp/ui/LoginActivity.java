package com.example.loginapp.ui;


import static com.example.loginapp.Constants.BASE_URL;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginapp.R;
import com.example.loginapp.service.ApiService;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    private TextInputLayout tiEmail;
    private TextInputLayout tiPassword;
    private ApiService apiService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tiEmail=findViewById(R.id.emailInput); //TODO NE STIMA
        tiPassword = findViewById(R.id.passwordInput);

        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiService = retrofit.create(ApiService.class);



        findViewById(R.id.loginBtn).setOnClickListener((view) -> {
            // login();
            // writeData();
        });


    }



    /*
    findViewById(R.id.loginBtn).setOnClickListener((view) -> {
        // login();
        // writeData();
    });
*/

}





/*
*
*  private void getPosts() {
        Call<List<Post>> call = jsonPlacholderApi.getPosts(4);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    tvResult.setText("Code: "+response.code());
                    return;
                }
                List<Post> posts = response.body();

                for (Post post: posts){
                    String content = "";
                    content += "ID: "+ post.getId() +"\n";
                    content += "USER ID: "+ post.getUserId() +"\n";
                    content += "TITLE: "+ post.getTitle() +"\n";
                    content += "TEXT: "+ post.getText() +"\n\n";

                    tvResult.append(content);
                }


            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }
*
* */