package com.easysolutions.dod_admin;

//import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;
//import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Random;

import static com.easysolutions.dod_admin.App.CHANNEL_ID;
//import static com.easysolutions.dod_admin.App.CHANNEL_ID1;

public class OTPService extends Service {


    //private int msgsSent=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("DOD-OTP Service")
                .setContentText("Sending OTP'S to Customers.")
                .setSmallIcon(R.drawable.ic_send_black)
                .build();
        startForeground(1, notification);
        startSendingOtps();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        //Log.i("92727","= stoped");
//        String uniqueActionString = "com.easysolutions.intents.unknown";
//        Intent broadcastIntent = new Intent(uniqueActionString);
//        this.sendBroadcast(broadcastIntent);
       if(MainActivity.check==false &&!MainActivity.check){
           Intent intent = new Intent(getApplicationContext(), OTPService.class);
           PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
           AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
           alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 60*1000, pendingIntent);
           Log.e("92727","onDestroy");
           super.onDestroy();
       }else{
           super.onDestroy();
       }

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent intent = new Intent(getApplicationContext(), OTPService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5000, pendingIntent);
        super.onTaskRemoved(rootIntent);
        Log.e("92727","onTask removed");
    }

    //    public void otpsentNotify(){
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID1)
//                .setContentTitle("DOD-OTP Sent")
//                .setContentText("An OTP has been sent to a customer.")
//                .setSmallIcon(R.drawable.ic_message_black_24dp)
//                .build();
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(99, notification);
//    }
    public void startSendingOtps(){
        //doing message sending through FIREBASE
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final SmsManager sms = SmsManager.getDefault();
        mDatabase.child("OTP").child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String checkStatus=snapshot.child("status").getValue().toString();
                    if(checkStatus!=null&&checkStatus.equals("plz_sent")){
                        Random random=new Random();
                        int otp = random.nextInt(999999);
                        //int otp=487687;
                        int v=otp/100000;
                        while(v==0){
                            Log.e("92727",v+" : "+otp);
                            otp=otp*10;
                            v=otp/100000;

                        }
                        // Log.d("92727",otp+"   status");
                        Intent intent = new Intent(getApplicationContext(), OTPService.class);
                        final PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                        final String msg = "Your requested OTP for DOD-EASY Solutions is " + otp;
                        sms.sendTextMessage(snapshot.getKey(), "03045", msg, pi, null);
                        mDatabase.child("OTP").child("Otp").child(snapshot.getKey()).child("otp").setValue(otp);
                        //msgsSent++;
                        //otpsentNotify();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        mDatabase.child("OTP").child("Otp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String OTP=dataSnapshot1.child("otp").getValue().toString();
                    if(OTP!=null){
                        mDatabase.child("OTP").child("Status").child(dataSnapshot1.getKey()).removeValue();
                        mDatabase.child("OTP").child(dataSnapshot1.getKey()).child("otp").setValue(OTP);
                        mDatabase.child("OTP").child("Otp").child(dataSnapshot1.getKey()).removeValue();
                    }
                    //Log.e("92727",OTP+"   otp");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Log.i("92727","= started");
    }
}
