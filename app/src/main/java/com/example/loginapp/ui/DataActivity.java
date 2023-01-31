package com.example.loginapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginapp.R;

public class DataActivity extends AppCompatActivity {

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        btnLogout=(Button)findViewById(R.id.logoutBtn);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataActivity.this, LoginActivity.class));
                //logout -> remove token -> pric u prvi app
            }
        });
    }
}

