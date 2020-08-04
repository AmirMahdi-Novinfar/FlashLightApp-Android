
package com.example.amirnovinfar.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.amirnovinfar.R;

public class FlashLightActivity extends AppCompatActivity {
    AppCompatImageButton imageButton, img_cheshmak;
    boolean hasflash, isflashon;
    MediaPlayer mediaPlayer;
    CameraManager camera;
    int chknum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);
        setupviews();
        issupportedflash();
        isflashon = false;

        imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                if (isflashon == false) {
                    TurnOnFlashlights();
                } else if (isflashon == true) {
                    TurnOffFlashlights();
                    imageButton.setImageResource(R.drawable.main_off);
                }

            }
        });
        img_cheshmak.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                if (isflashon) {
                    if (chknum == 1) {
                        for (int i = 0; i <= 20; i++) {
                            TurnOffFlashlights();
                            TurnOnFlashlights();
                        }
                        img_cheshmak.setImageResource(R.drawable.small_cheshmak_off);
                        chknum = 0;
                    } else {
                        img_cheshmak.setImageResource(R.drawable.small_cheshmak_off);
                        chknum = 1;
                        img_cheshmak.setImageResource(R.drawable.small_cheshmak);
                    }
                }
            }
        });
    }

    private void setupviews() {
        imageButton = (AppCompatImageButton) findViewById(R.id.btn_switch);
        img_cheshmak = (AppCompatImageButton) findViewById(R.id.img_cheshmak);
    }

    private void issupportedflash() {
        hasflash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasflash) {
            AlertDialog alertDialog = new AlertDialog.Builder(FlashLightActivity.this).create();
            alertDialog.setTitle("خطا!");
            alertDialog.setMessage("دستگاه شما از چراغ غوه پشتیبانی نمی کند و یا چراغ قوه شما آسیب دیده است.");
            alertDialog.setButton("خروج", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alertDialog.show();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void TurnOnFlashlights() {
        final CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String camid = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                cameraManager.setTorchMode(camid, true);
                isflashon = true;
            }

            Changebackground_btn_switch();
            playbtnsound();

        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void TurnOffFlashlights() {
        isflashon = false;
        final CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String camid = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                cameraManager.setTorchMode(camid, false);
            }
            Changebackground_btn_switch();
            playbtnsound();

        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

        }


    }

    public void Changebackground_btn_switch() {

        if (isflashon) {
            imageButton.setImageResource(R.drawable.main_on);
        } else if (isflashon == false) {
            imageButton.setImageResource(R.drawable.main_off);
        }
    }

    public void playbtnsound() {
        if (isflashon) {
            mediaPlayer = MediaPlayer.create(FlashLightActivity.this, R.raw.switch_on);
        } else {
            mediaPlayer = MediaPlayer.create(FlashLightActivity.this, R.raw.switch_off);
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayerkk) {
                mediaPlayer.release();
            }
        });

        mediaPlayer.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onResume() {
        super.onResume();
        if (hasflash) {
            TurnOnFlashlights();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onStart() {
        super.onStart();
        TurnOnFlashlights();
    }

}

