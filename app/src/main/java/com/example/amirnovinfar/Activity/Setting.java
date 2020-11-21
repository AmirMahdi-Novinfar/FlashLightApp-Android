package com.example.amirnovinfar.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.sdsmdg.tastytoast.TastyToast;

public class Setting extends AppCompatActivity {

    public static boolean soundon_off;
    SeekBar seekBar;
    int brightness;
    Context context;
    Toolbar toolbar;
    ImageView view,call_setting,home_setting,about_setting;
    SwitchCompat switchCompat;

    boolean canWrite;


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

        home_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        about_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setaboutusdialog();
            }
        });


        call_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting.this,Call_Us.class));
                finish();
            }
        });


    }


    private void setupviews() {
        seekBar = findViewById(R.id.change_noor);
        toolbar = findViewById(R.id.toolbar_setting);
        view = findViewById(R.id.img_back_setting);
        switchCompat = findViewById(R.id.sound_on_off);
        about_setting = findViewById(R.id.about_setting);
        call_setting = findViewById(R.id.call_setting);
        home_setting = findViewById(R.id.home_setting);


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
                        if (canWrite) {
                            int brightness2 = i * 255 / 255;
                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness2);

                        } else {
                            TastyToast.makeText(Setting.this, "در این قسمت این برنامه را انتخاب نموده و اجازه دادن تغیرات را به برنامه بدهید", Toast.LENGTH_LONG,TastyToast.ERROR).show();
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        canWrite = Settings.System.canWrite(context);
                    }

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

    private void setaboutusdialog() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.about_us_layout);
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GetBrightness();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            canWrite = Settings.System.canWrite(context);
        }
        if (!canWrite){

            TastyToast.makeText(Setting.this, "در این قسمت این برنامه را انتخاب نموده و اجازه دادن تغیرات را به برنامه بدهید", Toast.LENGTH_LONG,TastyToast.ERROR).show();
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            startActivity(intent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                canWrite = Settings.System.canWrite(context);
            }
        }
    }
}
