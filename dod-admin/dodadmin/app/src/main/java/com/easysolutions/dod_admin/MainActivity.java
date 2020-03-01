package com.easysolutions.dod_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

// ...
    Intent intent;
    public static boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=new Intent(this, OTPService.class);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    1234);
        }

    }


    public void startOtpService(View view) {
        ContextCompat.startForegroundService(this,intent);
        //startService(intent);
    }

    public void stopOtpService(View view) {
        check=true;
        stopService(intent);
        Toast.makeText(this,"Service stoped",Toast.LENGTH_SHORT).show();
    }
}
