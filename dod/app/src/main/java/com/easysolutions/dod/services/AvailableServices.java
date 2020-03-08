package com.easysolutions.dod.services;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import com.easysolutions.dod.chats.Chat_Threads;
import com.easysolutions.dod.ui.main.Orders;
import com.easysolutions.dod.R;
import com.easysolutions.dod.complaints.Complaints;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class AvailableServices extends AppCompatActivity {

    private static final String TAG = "AvailableServices";
    private SectionPAdapter msectionPageAdapter;
    ViewPager mviewPager;
    static Context context;
    static DatabaseReference databaseReference;
    static StorageReference storageReference;
    public static String phoneNumber, name;
    public  static boolean login=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_services);
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        name = intent.getStringExtra("Name");
        context = this;
        login=true;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        FragmentManager fragmentManager = getSupportFragmentManager();
        msectionPageAdapter = new SectionPAdapter(fragmentManager, 1);
        mviewPager = findViewById(R.id.view_pager);
        setupViewPager();
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);
        registerToken(phoneNumber);
    }

    private void registerToken(final String phoneNumber) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()){
                    return;
                }
                String token=task.getResult().getToken();
                databaseReference.child("USERS").child(phoneNumber).child("token").setValue(token);
            }
        });
    }
    private void setupViewPager() {

        msectionPageAdapter.addFragment(new Tab_Conveyance(), "Conveyance");
        msectionPageAdapter.addFragment(new Tab_Photocopy(), "Photocopy");
        msectionPageAdapter.addFragment(new Tab_printdoc(), "Print Doc");
        mviewPager.setAdapter(msectionPageAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.availableservices, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chats:
                //do this
                Intent i=new Intent(this, Chat_Threads.class);
                i.putExtra("phoneNumber",phoneNumber);
                i.putExtra("name",name);
                startActivity(i);
                break;
            case R.id.orders:
                //do this
                Intent intent = new Intent(this, Orders.class);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("name",name);
                startActivity(intent);
                break;
            case R.id.complaints:
                Intent i2=new Intent(this, Complaints.class);
                i2.putExtra("phoneNumber",phoneNumber);
                i2.putExtra("name",name);
                startActivity(i2);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        login=false;
                        finish();
                    }
                }).create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}