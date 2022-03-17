package ir.iamnovinfar.FlashLights.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ir.iamnovinfar.FlashLights.R;

public class About_usActivty extends Activity {

    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_layout);
        tv=findViewById(R.id.website);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                String uri="https://iamnovinfar.ir";
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

    }
}
