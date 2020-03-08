package com.dod.DOD_ServiceProviders.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


import com.dod.DOD_ServiceProviders.Dashboard;
import com.dod.DOD_ServiceProviders.LoginActivity;
import com.dod.DOD_ServiceProviders.R;
import com.dod.DOD_ServiceProviders.ui.chat.Messaging;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sendOAndAboveNOtification(remoteMessage);
        }else{
            sendNormalNOtification(remoteMessage);
        }

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("TAGS").child("customers").child(Dashboard.phoneNumber).child("token").setValue(s);
    }

    private void sendNormalNOtification(RemoteMessage remoteMessage) {
        String subtxt="";
        String icon=remoteMessage.getData().get("icon");
        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("message");
        String ref=remoteMessage.getData().get("intent");
        Intent intent = null;
        if (Dashboard.login) {
            if (ref.equals("0")) {
                intent = new Intent(this, Dashboard.class);
                String name = remoteMessage.getData().get("name");
                String userID = remoteMessage.getData().get("hisID");
                intent.putExtra("phoneNumber", userID);
                intent.putExtra("Name", name);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else if (ref.equals("1")) {
                intent = new Intent(this, Messaging.class);
                String cusName = remoteMessage.getData().get("cusName");
                String cusNo = remoteMessage.getData().get("cusNo");
                String orderNo = remoteMessage.getData().get("orderNo");
                String proName = remoteMessage.getData().get("proName");
                String proNo = remoteMessage.getData().get("proNo");
                intent.putExtra("proNo", proNo);
                intent.putExtra("proName", proName);
                intent.putExtra("cusName", cusName);
                intent.putExtra("cusNo", cusNo);
                intent.putExtra("orderNo", orderNo);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            }else if(ref.equals("2")){
                intent = new Intent(this, Dashboard.class);
                String phoneNumber=remoteMessage.getData().get("phoneNumber");
                String name=remoteMessage.getData().get("Name");
                String tab=remoteMessage.getData().get("tab");


                intent.putExtra("phoneNumber",phoneNumber);
                intent.putExtra("Name",name);
                intent.putExtra("tab",tab);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        }else {
            Log.d("92727", "sendOAndAboveNOtification: login");
            intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }


        if(ref.equals("1")){
            subtxt=remoteMessage.getData().get("subtext");
        }

        Log.d("92727", "sendNormalNOtification: mormal");
        PendingIntent pIntent=PendingIntent.getActivity(this,92727 , intent,PendingIntent.FLAG_ONE_SHOT );

        Uri soundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this )
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSubText(subtxt)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(soundUri);
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(92727,builder.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendOAndAboveNOtification(RemoteMessage remoteMessage) {
        String subtxt="";
        String icon=remoteMessage.getData().get("icon");
        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("message");
        String ref=remoteMessage.getData().get("intent");
        Intent intent = null;
        if (Dashboard.login) {
            if(ref.equals("0")) {
                intent = new Intent(this, Dashboard.class);
                String name=remoteMessage.getData().get("name");
                String userID=remoteMessage.getData().get("hisID");
                intent.putExtra("phoneNumber",userID);
                intent.putExtra("Name",name);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }else if(ref.equals("1")){
                Log.d("92727", "sendOAndAboveNOtification: msgs");
                intent = new Intent(this, Messaging.class);
                String cusName=remoteMessage.getData().get("cusName");
                String cusNo=remoteMessage.getData().get("cusNo");
                String orderNo=remoteMessage.getData().get("orderNo");
                String proName=remoteMessage.getData().get("proName");
                String proNo=remoteMessage.getData().get("proNo");

                intent.putExtra("proNo",proNo);
                intent.putExtra("proName",proName);
                intent.putExtra("cusName",cusName);
                intent.putExtra("cusNo",cusNo);
                intent.putExtra("orderNo",orderNo);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            }else if(ref.equals("2")){
                intent = new Intent(this, Dashboard.class);
                String phoneNumber=remoteMessage.getData().get("proNo");
                String name=remoteMessage.getData().get("proName");
                String tab=remoteMessage.getData().get("tab");

                intent.putExtra("phoneNumber",phoneNumber);
                intent.putExtra("Name",name);
                intent.putExtra("tab",tab);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }else if(ref.equals("admin")){
                String no=remoteMessage.getData().get("no");
                String cmpID=remoteMessage.getData().get("cmpID");
                intent=new Intent(this, com.dod.DOD_ServiceProviders.ui.complaints.Messaging.class);
                intent.putExtra("no",no);
                intent.putExtra("cmpID",cmpID);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            }

        } else {
            Log.d("92727", "sendOAndAboveNOtification: login");
                intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }

        if(ref.equals("1")|| ref.equals("2")){
            subtxt=remoteMessage.getData().get("subtext");
        }

        PendingIntent pIntent=PendingIntent.getActivity(this,92727 , intent,PendingIntent.FLAG_ONE_SHOT );

        Uri soundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoAndAboveNotification notification1=new OreoAndAboveNotification(this);
        Log.d("92727", "sendOAndAboveNOtification: "+subtxt);
        Notification.Builder builder=notification1.getNotifictions(title,body,pIntent,soundUri,icon,subtxt);
        notification1.getManager().notify(92727,builder.build());

    }
}
