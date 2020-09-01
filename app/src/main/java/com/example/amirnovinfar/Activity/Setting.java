package com.example.amirnovinfar.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amirnovinfar.R;

public class Setting extends AppCompatActivity {

    SeekBar seekBar;
    int brightness;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setupviews();
        context = getApplicationContext();
        GetBrightness();
        SetBrightness();
    }


    private void setupviews() {
        seekBar = findViewById(R.id.change_noor);

    }

    private void GetBrightness() {

        try {
            brightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        seekBar.setProgress(brightness);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void SetBrightness() {
        boolean canWrite = Settings.System.canWrite(context);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    boolean canWrite = Settings.System.canWrite(context);

                    if (canWrite) {
                        int brightness2=i*255/255;
                        Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE,
                                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                        Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,brightness2);

                    }else {
                        Intent intent= new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        context.startActivity(intent);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

    }
}
