package com.example.amirnovinfar.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.example.amirnovinfar.R;

public class Setting extends AppCompatActivity {

    public static boolean soundon_off;
    SeekBar seekBar;
    int brightness;
    Context context;
    Toolbar toolbar;
    ImageView view;
    SwitchCompat switchCompat;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setSupportActionBar(toolbar);
        setupviews();
        context = getApplicationContext();
        GetBrightness();
        SetBrightness();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        GETandSETswitchdata();


    }


    private void setupviews() {
        seekBar = findViewById(R.id.change_noor);
        toolbar = findViewById(R.id.toolbar_setting);
        view = findViewById(R.id.img_back_setting);
        switchCompat = findViewById(R.id.sound_on_off);


    }

    private void GetBrightness() {

        try {
            brightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        seekBar.setProgress(brightness);

    }

    private void SetBrightness() {
        try {
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        boolean canWrite = false;
                        canWrite = Settings.System.canWrite(context);

                        if (canWrite) {
                            int brightness2 = i * 255 / 255;
                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness2);

                        } else {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                            startActivity(intent);
                        }

                    } else {

                        int brightness2 = i * 255 / 255;
                        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness2);

                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }


    }


    private void GETandSETswitchdata() {
        SharedPreferences sharedPreferences=getSharedPreferences("soundamir",MODE_PRIVATE);
        switchCompat.setChecked(sharedPreferences.getBoolean("soundvalue",true));

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchCompat.isChecked()){
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putBoolean("soundvalue",true);
                    editor.apply();

                }else {
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putBoolean("soundvalue",false);
                    editor.apply();
                }
            }
        });


    }


}
