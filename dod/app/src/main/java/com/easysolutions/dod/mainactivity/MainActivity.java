package com.easysolutions.dod.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.easysolutions.dod.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new frag_main()).commit();
    }
}
