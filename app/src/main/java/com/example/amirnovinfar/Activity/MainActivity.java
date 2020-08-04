package com.example.amirnovinfar.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amirnovinfar.Adapter.My_view_page_adapter;
import com.example.amirnovinfar.R;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    public Button btn, btn2;
    My_view_page_adapter page_adapter;
    LinearLayout dots_lay;

    int[] layid={
      R.layout.fragment_blank,
      R.layout.fragment_slide_2,
      R.layout.fragment_slide_3,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        page_adapter = new My_view_page_adapter(MainActivity.this);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(page_adapter);
        dots_lay = findViewById(R.id.dots_layout);
        Dots();

      btn = findViewById(R.id.btn_next);
        btn2 = findViewById(R.id.btn_per);
        btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               viewPager.setCurrentItem(NextViewPagerItem(1), true);
          }
     });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(PerViewPagerItem(1), true);
            }
        });


    }

    public int NextViewPagerItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    public int PerViewPagerItem(int i) {
        return viewPager.getCurrentItem() - i;
    }

    public void Dots() {
        TextView[] dots = new TextView[3];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
            dots[i].setTextColor(Color.WHITE);
            dots_lay.addView(dots[i]);
        }

    }
}
