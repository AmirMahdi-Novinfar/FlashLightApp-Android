package com.example.amirnovinfar.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.amirnovinfar.Adapter.My_view_page_adapter;
import com.example.amirnovinfar.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
   private ViewPager2 viewPager;
   public Button btn;
   My_view_page_adapter page_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        page_adapter=new My_view_page_adapter(MainActivity.this);
        viewPager=findViewById(R.id.viewpager);
        viewPager.setAdapter(page_adapter);
    }
}
