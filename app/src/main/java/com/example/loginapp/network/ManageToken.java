package com.example.loginapp.network;

import android.content.Context;
import android.content.SharedPreferences;

public class ManageToken {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public ManageToken(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
    }

}
