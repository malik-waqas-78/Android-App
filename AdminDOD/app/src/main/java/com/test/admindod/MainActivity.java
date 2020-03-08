package com.test.admindod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.test.admindod.complaints.ResolveComplaints;
import com.test.admindod.orders.FragmentAllOrders;
import com.test.admindod.providers.Servie_Providers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    public static String tab="";
    String toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.nav_side_view);
        TextView textView=navigationView.getHeaderView(0).findViewById(R.id.username);
        textView.setText("@root");
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
            getSupportActionBar().setTitle("All Orders");
            //toolbar_title="0";
            //Log.e("92727","orders");
        if(intent.hasExtra("admin0")){
            tab="0";
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ResolveComplaints()).commit();
            navigationView.setCheckedItem(R.id.mi_complaints);
        }else if(intent.hasExtra("admin1")){
            tab="1";
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ResolveComplaints()).commit();
            navigationView.setCheckedItem(R.id.mi_complaints);
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentAllOrders()).commit();
            navigationView.setCheckedItem(R.id.mi_orders);
        }

            getToken();
    }
    private void getToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
//To do//
                            return;
                        }

// Get the Instance ID token//
                        String token = task.getResult().getToken();
                        FirebaseDatabase.getInstance().getReference().child("Admin").child("token").setValue(token);

                    }
                });
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.mi_orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentAllOrders()).commit();
                toolbar.setTitle("All Orders");
                toolbar_title="0";
                break;
            case R.id.mi_service_provider:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Servie_Providers()).commit();
                toolbar.setTitle("Service Providers");
                toolbar_title="1";
                break;
            case R.id.mi_complaints:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ResolveComplaints()).commit();
                toolbar.setTitle("Complaints");
                toolbar_title="1";
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main_menu,menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                //do this
                break;
            default:
                return false;
        }
        return true;
    }
}
