package com.easysolutions.dod.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.easysolutions.dod.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.easysolutions.dod.ui.main.SectionsPagerAdapter;

public class Orders extends AppCompatActivity {

    public static String PHONENUMBE="",name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_layout);
        Intent i=getIntent();
        PHONENUMBE=i.getStringExtra("phoneNumber");
        name=i.getStringExtra("name");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        if(i.hasExtra("tab")){
            viewPager.setCurrentItem(1);
        }
    }
}