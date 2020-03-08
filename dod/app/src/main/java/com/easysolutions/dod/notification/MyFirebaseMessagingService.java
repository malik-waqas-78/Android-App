package com.easysolutions.dod.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.easysolutions.dod.R;
import com.easysolutions.dod.chats.Messaging;
import com.easysolutions.dod.complaints.MessagingChat;
import com.easysolutions.dod.mainactivity.MainActivity;
import com.easysolutions.dod.services.AvailableServices;
import com.easysolutions.dod.ui.main.Orders;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
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
        databaseReference.child("TAGS").child("customers").child(AvailableServices.phoneNumber).child("token").setValue(s);
    }

    private void sendNormalNOtification(RemoteMessage remoteMessage) {
        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("message");
        String subText="";
        String ref=remoteMessage.getData().get("intent");
        Intent intent=null;
        if(AvailableServices.login){
            if(ref.equals("0")){
                subText="Congrats!";
                String cusName=remoteMessage.getData().get("cusName");
                String cusNo=remoteMessage.getData().get("cusNo");
                intent=new Intent(this, Orders.class);
                intent.putExtra("phoneNumber",cusNo);
                intent.putExtra("name",cusName);
                intent.putExtra("tab","1");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }else if(ref.equals("1")){
                subText="New Message!";
                String cusName=remoteMessage.getData().get("cusName");
                String cusNo=remoteMessage.getData().get("cusNo");
                String proName=remoteMessage.getData().get("proName");
                String proNo=remoteMessage.getData().get("proNo");
                String orderNo=remoteMessage.getData().get("orderNo");
                intent=new Intent(this, Messaging.class);
                intent.putExtra("cusNo",cusNo);
                intent.putExtra("cusName",cusName);
                intent.putExtra("proName",proName);
                intent.putExtra("proNo",proNo);
                intent.putExtra("orderNo",orderNo);
                intent.putExtra("tab","1");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        }else{
            intent=new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }


         PendingIntent pIntent=PendingIntent.getActivity(this,92727 , intent,PendingIntent.FLAG_ONE_SHOT );

        Uri soundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this )
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentIntent(pIntent)
                .setSubText(subText)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(soundUri);
        if(ref.equals("0")){
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(body));
        }else{
            builder.setContentText(body);
        }


        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(92727,builder.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendOAndAboveNOtification(RemoteMessage remoteMessage) {

        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("message");
        String subText="";
        String ref=remoteMessage.getData().get("intent");
        Intent intent=null;
        if(AvailableServices.login){
            if(ref.equals("0")){

                String cusName=remoteMessage.getData().get("cusName");
                String cusNo=remoteMessage.getData().get("cusNo");
                intent=new Intent(this, Orders.class);
                intent.putExtra("phoneNumber",cusNo);
                intent.putExtra("name",cusName);
                intent.putExtra("tab","1");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }else if(ref.equals("1")){

                String cusName=remoteMessage.getData().get("cusName");
                String cusNo=remoteMessage.getData().get("cusNo");
                String proName=remoteMessage.getData().get("proName");
                String proNo=remoteMessage.getData().get("proNo");
                String orderNo=remoteMessage.getData().get("orderNo");
                intent=new Intent(this, Messaging.class);
                intent.putExtra("cusNo",cusNo);
                intent.putExtra("cusName",cusName);
                intent.putExtra("proName",proName);
                intent.putExtra("proNo",proNo);
                intent.putExtra("orderNo",orderNo);
                intent.putExtra("tab","1");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }else if(ref.equals("admin")){
                String no=remoteMessage.getData().get("no");
                String cmpID=remoteMessage.getData().get("cmpID");
                intent=new Intent(this, MessagingChat.class);
                intent.putExtra("no",no);
                subText="New Complaint Message";
                intent.putExtra("cmpID",cmpID);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        }else{
            intent=new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        if(ref.equals("0")){
            subText="Congrats!";
        }else if(ref.equals("1")){
            subText="New Message!";
        }else if(ref.equals("admin")){
            subText="New Complaint Message";
        }
        PendingIntent pIntent=PendingIntent.getActivity(this,92727 , intent,PendingIntent.FLAG_ONE_SHOT );
        Uri soundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoAndAboveNotification notification1=new OreoAndAboveNotification(this);
        Notification.Builder builder=notification1.getNotifictions(title,body,pIntent,soundUri,ref,subText);

        notification1.getManager().notify(92727,builder.build());

    }
}
