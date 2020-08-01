
package com.example.amirnovinfar.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
    ImageButton imageButton, img_cheshmak;
    boolean hasflash, isflashon;
    MediaPlayer mediaPlayer;
    CameraManager camera;



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
                if (isflashon==false){
                    TurnOnFlashlights();
                }

                   else if (isflashon==true){
                    TurnOffFlashlights();
                }

            }
        });

        img_cheshmak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    TurnOffFlashlights();
                }
            }
        });
    }

    private void setupviews() {
        imageButton = findViewById(R.id.btn_switch);
        img_cheshmak = findViewById(R.id.img_cheshmak);
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
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

        }

    }
}
