package com.example.amirnovinfar.Activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.amirnovinfar.R;
import com.sdsmdg.tastytoast.TastyToast;

public class FlashLightActivity extends AppCompatActivity {
    AppCompatImageButton imageButton, img_cheshmak;
    boolean hasflash, isflashon;
    MediaPlayer mediaPlayer;
    int chknum = 0;
    Camera camera;
    TextView battery_status;
    ImageView img_battery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setupviews();
        issupportedflash();
        isflashon = false;
        imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                    if (isflashon == false) {
                        TurnOnFlashlights();
                    } else if (isflashon == true) {
                        TurnOffFlashlights();
                        imageButton.setImageResource(R.drawable.main_off);
                    }

                } else {

                    if (isflashon == false) {
                        TurnOnFlashlightsofapilast();
                    } else if (isflashon == true) {
                        TurnOffFlashlightsofapilast();
                        imageButton.setImageResource(R.drawable.main_off);
                    }
                }


            }
        });
        img_cheshmak.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                if (isflashon) {
                    for (int i = 0; i <10; i++) {
                        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                                TurnOffFlashlights();
                                TurnOnFlashlights();
                            } else {
                                TurnOffFlashlightsofapilast();
                                TurnOnFlashlightsofapilast();
                            }

                    }
                }

                else {
                    TastyToast.makeText(FlashLightActivity.this,"لطفا چراغ قوه را روشن کنید.",TastyToast.LENGTH_LONG,TastyToast.ERROR).show();
                }



            }
        });
        IntentFilter intentFilter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver,intentFilter);

    }

    private void setupviews() {
        imageButton = findViewById(R.id.btn_switch);
        img_cheshmak = findViewById(R.id.img_cheshmak);
        battery_status = findViewById(R.id.battery_status);
        img_battery = findViewById(R.id.img_battery);


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
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                TurnOnFlashlights();

            } else {
                TurnOnFlashlightsofapilast();
            }


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            TurnOnFlashlights();

        } else {
            TurnOnFlashlightsofapilast();
        }

    }

    private void TurnOnFlashlightsofapilast() {
        try {

            camera = Camera.open();
            Camera.Parameters p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
            camera.startPreview();
            isflashon = true;
            Changebackground_btn_switch();
            playbtnsound();

        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void TurnOffFlashlightsofapilast() {
        try {

            camera.stopPreview();
            isflashon = false;
            camera.release();
            camera = null;

        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int darsad=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            battery_status.setText(String.valueOf(darsad) + "%");
            if (darsad==100){
                img_battery.setImageResource(R.drawable.ic_battery_full);
            }else if (darsad >= 1 && darsad <= 15){
                img_battery.setImageResource(R.drawable.ic_battery_alert_black_24dp);
                battery_status.append("\n باطری شما ضعیف است");

            }else if (darsad >= 20 && darsad <= 29){
                img_battery.setImageResource(R.drawable.ic_battery_20);

            }else if (darsad >= 30 && darsad <= 39){
                img_battery.setImageResource(R.drawable.ic_battery_30);

            }else if (darsad >= 40 && darsad <= 49){
                img_battery.setImageResource(R.drawable.ic_battery_30);

            } else if (darsad >= 50 && darsad <= 59){
                img_battery.setImageResource(R.drawable.ic_battery_50);

            }else if (darsad >= 60 && darsad <= 69){
                img_battery.setImageResource(R.drawable.ic_battery_60);

            }else if (darsad >= 70 && darsad <= 79){
                img_battery.setImageResource(R.drawable.ic_battery_60);

            } else if (darsad >= 80 && darsad <= 89){
                img_battery.setImageResource(R.drawable.ic_battery_80);

            }else if (darsad >= 90 && darsad <= 99){
                img_battery.setImageResource(R.drawable.ic_battery_90_black_24dp);
            }




        }
    };
}

