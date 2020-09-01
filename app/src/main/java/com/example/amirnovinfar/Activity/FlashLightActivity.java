package com.example.amirnovinfar.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.amirnovinfar.R;
import com.google.android.material.navigation.NavigationView;
import com.sdsmdg.tastytoast.TastyToast;
import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;

public class FlashLightActivity extends AppCompatActivity {
    AppCompatImageButton imageButton, img_cheshmak;
    boolean hasflash, isflashon;
    MediaPlayer mediaPlayer;
    int chknum = 0;
    Camera camera;
    TextView battery_status, battery_status2,setting;
    ImageView img_battery, open_drawer, img_help, setting_FlashLight;
    DrawerLayout drawerlayout1;
    NavigationView navigationView;
    Notification.Builder compat;
    LinearLayout linearLayout;
    NotificationManager manager;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setupviews();
        isflashon = false;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(0);
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
                    for (int i = 0; i < 10; i++) {
                        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                            TurnOffFlashlights();
                            TurnOnFlashlights();
                        } else {
                            TurnOffFlashlightsofapilast();
                            TurnOnFlashlightsofapilast();
                        }

                    }
                } else {
                    TastyToast.makeText(FlashLightActivity.this, "لطفا چراغ قوه را روشن کنید.", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                }


            }
        });
        open_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout1.openDrawer(Gravity.RIGHT);
            }
        });
        img_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showinfo();
              }
        });
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver, intentFilter);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.about_us) {
                    setaboutusdialog();
                    drawerlayout1.closeDrawers();
                } else if (id == R.id.call_us) {
                    startActivity(new Intent(FlashLightActivity.this, Call_Us.class));
                    drawerlayout1.closeDrawers();
                } else if (id == R.id.setting) {
                    startActivity(new Intent(FlashLightActivity.this, Setting.class));
                    drawerlayout1.closeDrawers();
                }else if (id == R.id.quit) {
                   finish();
                }
                return true;
            }
        });
        manager.cancel(0);

        setting_FlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FlashLightActivity.this,Setting.class));
            }
        });


    }

    private void setupviews() {
        imageButton = findViewById(R.id.btn_switch);
        img_cheshmak = findViewById(R.id.img_cheshmak);
        battery_status = findViewById(R.id.battery_status);
        img_battery = findViewById(R.id.img_battery);
        drawerlayout1 = findViewById(R.id.drawerlayout1);
        navigationView = findViewById(R.id.nav_main);
        open_drawer = findViewById(R.id.open_drawer);
        battery_status2 = findViewById(R.id.battery_status2);
        img_help = findViewById(R.id.img_help);
        linearLayout = findViewById(R.id.layoyt_battry);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        setting_FlashLight =  findViewById(R.id.setting_toolbar_FlashLight);



    }

    private boolean issupportedflash() {
        hasflash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (hasflash) {
            return true;
        } else {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void TurnOnFlashlights() {
        if (issupportedflash()) {
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

        } else {

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
    private void TurnOffFlashlights() {
        if (issupportedflash()) {
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
        } else {

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
        }
    }

    private void TurnOffFlashlightsofapilast() {
        if (issupportedflash()) {
            try {
                camera.stopPreview();
                isflashon = false;
                camera.release();
                camera = null;

            } catch (Exception e) {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(FlashLightActivity.this);
            alertDialog.setTitle("خطا!");
            alertDialog.setMessage("دستگاه شما از چراغ غوه پشتیبانی نمی کند و یا چراغ قوه شما آسیب دیده است.");
            alertDialog.setPositiveButton("خروج", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alertDialog.show();
        }
    }

    private void setaboutusdialog() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.about_us_layout);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPause() {
        super.onPause();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStop() {
        super.onStop();
        CreateNotification();
        manager.notify(0, compat.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void CreateNotification() {
        Intent intent = new Intent(FlashLightActivity.this, FlashLightActivity.class);
        PendingIntent intent1 = PendingIntent.getActivity(FlashLightActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        compat = new Notification.Builder(this);
        compat.setSmallIcon(R.drawable.ic_highlight_black_24dp);
        compat.setContentText("برنامه چراغ قوه در حال اجرا است");
        compat.setColor(Color.RED);
        compat.setContentTitle("برنامه چراغ قوه");
        Notification.Action action = new Notification.Action(R.drawable.ic_arrow_back, "بازگشت به برنامه", intent1);
        compat.addAction(action);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.cancel(0);
    }

     void showinfo() {
       FancyShowCaseView view1=new FancyShowCaseView.Builder(this)
               .focusOn(open_drawer)
               .title("استفاده از امکانات دیگر در برنامه")
               .titleStyle(R.style.Help,Gravity.CENTER_HORIZONTAL)
               .build();
         FancyShowCaseView view2=new FancyShowCaseView.Builder(this)
                 .focusOn(setting)
                 .title("تنظیمات برنامه")
                 .titleStyle(R.style.Help,Gravity.CENTER_HORIZONTAL)
                 .build();
         FancyShowCaseView view3=new FancyShowCaseView.Builder(this)
                 .focusOn(imageButton)
                 .title("روشن کردن چراغ قوه ")
                 .titleStyle(R.style.Help,Gravity.START)
                 .build();
         FancyShowCaseView view4=new FancyShowCaseView.Builder(this)
                 .focusOn(img_cheshmak)
                 .title("استفاه از حالت چشمک زن چراغ قوه")
                 .titleStyle(R.style.Help, Gravity.CENTER_HORIZONTAL)
                 .build();

         FancyShowCaseView view5=new FancyShowCaseView.Builder(this)
                 .focusOn(linearLayout)
                 .title("وضعیت باطری دستگاه")
                 .titleStyle(R.style.Help, Gravity.BOTTOM)
                 .focusShape(FocusShape.ROUNDED_RECTANGLE)
                 .build();
         FancyShowCaseQueue queue=new FancyShowCaseQueue();
         queue.add(view3);
         queue.add(view4);
         queue.add(view2);
         queue.add(view1);
         queue.add(view5);
         queue.show();


     }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int darsad = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            battery_status.setText(String.valueOf(darsad) + "%");
            if (darsad == 100) {
                img_battery.setImageResource(R.drawable.ic_battery_full);
            } else if (darsad >= 1 && darsad <= 15) {
                img_battery.setImageResource(R.drawable.ic_battery_alert_black_24dp);
                battery_status2.setText("باطری شما ضعیف است");
            } else if (darsad >= 20 && darsad <= 29) {
                img_battery.setImageResource(R.drawable.ic_battery_20);

            } else if (darsad >= 30 && darsad <= 39) {
                img_battery.setImageResource(R.drawable.ic_battery_30);

            } else if (darsad >= 40 && darsad <= 49) {
                img_battery.setImageResource(R.drawable.ic_battery_30);

            } else if (darsad >= 50 && darsad <= 59) {
                img_battery.setImageResource(R.drawable.ic_battery_50);

            } else if (darsad >= 60 && darsad <= 69) {
                img_battery.setImageResource(R.drawable.ic_battery_60);

            } else if (darsad >= 70 && darsad <= 79) {
                img_battery.setImageResource(R.drawable.ic_battery_60);

            } else if (darsad >= 80 && darsad <= 89) {
                img_battery.setImageResource(R.drawable.ic_battery_80);
            } else if (darsad >= 90 && darsad <= 99) {
                img_battery.setImageResource(R.drawable.ic_battery_90_black_24dp);
            }
        }
    };
}

