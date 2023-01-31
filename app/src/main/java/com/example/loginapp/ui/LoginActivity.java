package com.example.loginapp.ui;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginapp.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.loginBtn).setOnClickListener((view) -> {
            // login();
            // writeData();
        });


    }

}
