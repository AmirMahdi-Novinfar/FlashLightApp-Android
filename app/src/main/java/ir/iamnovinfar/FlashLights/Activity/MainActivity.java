package ir.iamnovinfar.FlashLights.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import ir.iamnovinfar.FlashLights.Adapter.My_view_page_adapter;
import ir.iamnovinfar.FlashLights.Adapter.ViewpagerAnimation;
import ir.iamnovinfar.FlashLights.R;

public class MainActivity extends AppCompatActivity {
    public Button btn, btn2;
    My_view_page_adapter page_adapter;
    LinearLayout dots_lay;
    SharedPreferences sharedPreferences;
    boolean islogin;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        changebackgroundstatusbar();
        page_adapter = new My_view_page_adapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(page_adapter);

        ViewpagerAnimation animation = new ViewpagerAnimation();
        viewPager.setPageTransformer(true, animation);


        dots_lay = findViewById(R.id.dots_layout);
        Dots(0);

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
                sharedPreferences = getSharedPreferences("islogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                startActivity(new Intent(MainActivity.this, FlashLightActivity.class));
                islogin = true;
                editor.putBoolean("ISLOGIN", islogin);
                editor.apply();
                finish();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Dots(position);
                if (position == 3 - 1) {
                    btn2.setVisibility(View.GONE);
                    btn.setText("بزن بریم");
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sharedPreferences = getSharedPreferences("islogin", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            startActivity(new Intent(MainActivity.this, FlashLightActivity.class));
                            islogin = true;
                            editor.putBoolean("ISLOGIN", islogin);
                            editor.apply();
                            finish();
                        }
                    });
                } else {
                    btn2.setVisibility(View.VISIBLE);
                    btn.setText("بعدی");
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewPager.setCurrentItem(NextViewPagerItem(1), true);
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public int NextViewPagerItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    public void Dots(int pagenumber) {
        TextView[] dots = new TextView[3];
        dots_lay.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
            dots[i].setTextColor(ContextCompat.getColor(this,
                    (i == pagenumber ? R.color.dotsactive : R.color.dotsinactive)));
            dots_lay.addView(dots[i]);
        }

    }

    private void changebackgroundstatusbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

    }
}
