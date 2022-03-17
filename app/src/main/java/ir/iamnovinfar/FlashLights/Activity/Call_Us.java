package ir.iamnovinfar.FlashLights.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ir.iamnovinfar.FlashLights.R;

public class Call_Us extends AppCompatActivity {

    EditText subject, body;
    Button send_btn,connect_via_tel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__us);
        setupviews();
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();

            }
        });
        connect_via_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                String uri="https://iamnovinfar.ir";
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

    }

    private void setupviews() {
        subject = findViewById(R.id.edt_subject);
        body = findViewById(R.id.edt_body);
        send_btn = findViewById(R.id.btn_send_payam);
        connect_via_tel = findViewById(R.id.connect_via_tel);

    }

    private void getdata() {
        String subjects = subject.getText().toString().trim();
        String bodys = body.getText().toString().trim();
        String email[] = {"a.novinfar80@yahoo.com"};
        if (subjects.isEmpty()) {
            Toast.makeText(this, "لطفا عنوان پیام را وارد کنید", Toast.LENGTH_SHORT).show();
        } else {
            if (bodys.isEmpty()) {
                Toast.makeText(this, "لطفا متن پیام را وارد کنید", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("انتخاب کنید");
                builder.setIcon(R.drawable.selectlogo);
                builder.setMessage("از گزینه های برای ارتباط استفاده کنید");
                builder.setPositiveButton("ارتباط با ایمیل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_EMAIL, email);
                            intent.putExtra(Intent.EXTRA_SUBJECT, subjects);
                            intent.putExtra(Intent.EXTRA_TEXT, bodys);
                            intent.setType("text/plain");
                            startActivity(intent);

                        } catch (Exception e) {
                            Toast.makeText(Call_Us.this, "برنامه ای برای ارسال ایمیل وجود ندارد", Toast.LENGTH_LONG).show();
                            Toast.makeText(Call_Us.this, "برنامه ای برای ارسال ایمیل نصب کرده و دوباره امتحان کنید", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNeutralButton("ارتباط با sms", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                            smsIntent.setType("vnd.android-dir/mms-sms");
                            smsIntent.putExtra("address", "09375311696");
                            smsIntent.putExtra("sms_body", subjects + bodys);
                            startActivity(smsIntent);

                        } catch (Exception e) {
                            Toast.makeText(Call_Us.this, "برنامه ای برای ارسال sms وجود ندارد", Toast.LENGTH_LONG).show();
                            Toast.makeText(Call_Us.this, "برنامه ای برای ارسال sms نصب کرده و دوباره امتحان کنید", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder.show();
            }
        }
    }
}
