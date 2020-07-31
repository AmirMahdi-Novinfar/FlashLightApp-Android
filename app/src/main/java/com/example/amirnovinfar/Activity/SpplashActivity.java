package com.example.amirnovinfar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;

import com.example.amirnovinfar.R;

public class SpplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences=getSharedPreferences("islogin", MODE_PRIVATE);
        boolean islogin=sharedPreferences.getBoolean("ISLOGIN",false);
        if (!islogin) {
            startActivity(new Intent(SpplashActivity.this,MainActivity.class));
            finish();
        }else {
            startActivity(new Intent(SpplashActivity.this,FlashLightActivity.class));
            finish();

        }

    }
}
