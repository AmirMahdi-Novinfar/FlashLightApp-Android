package com.example.amirnovinfar.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageButton;
import com.example.amirnovinfar.R;

public class FlashLightActivity extends AppCompatActivity {
    ImageButton imageButton;
    boolean hasflash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);
        setupviews();
        issupportedflash();
    }

    private void setupviews() {
        imageButton = findViewById(R.id.btn_switch);
    }

    private void issupportedflash() {

    }
}
