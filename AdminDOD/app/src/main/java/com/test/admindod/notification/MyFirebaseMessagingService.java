package com.test.admindod.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.test.admindod.MainActivity;
import com.test.admindod.R;
import com.test.admindod.complaints.Customers_Complaints;
import com.test.admindod.complaints.MessagingChat;
import com.test.admindod.complaints.ResolveComplaints;


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
        databaseReference.child("Admin").child("token").setValue(s);
    }

    private void sendNormalNOtification(RemoteMessage remoteMessage) {
        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("message");
        String subText=remoteMessage.getData().get("subtext");
        String ref=remoteMessage.getData().get("intent");
        Intent intent=null;
        if(ref.equals("admin0")||ref.equals("admin1")){
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
        if(ref.equals("admin0")){
            intent=new Intent(this, MainActivity.class);
            intent.putExtra("admin0","0");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else if(ref.equals("admin1")){
            intent=new Intent(this, MainActivity.class);
            intent.putExtra("admin1","1");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else if(ref.equals("admin2")){
            intent=new Intent(this, MessagingChat.class);
            String no=remoteMessage.getData().get("no");
            String name=title;
            String cmpID = remoteMessage.getData().get("cmpID");
            intent.putExtra("no",no);
            intent.putExtra("name",name);
            intent.putExtra("cmpID",cmpID);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }


        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(92727,builder.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendOAndAboveNOtification(RemoteMessage remoteMessage) {

        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("message");
        String subText=remoteMessage.getData().get("subtext");
        String ref=remoteMessage.getData().get("intent");
        Intent intent=null;
        if(ref.equals("admin0")){
            intent=new Intent(this, MainActivity.class);
            intent.putExtra("admin0","0");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else if(ref.equals("admin1")){
            intent=new Intent(this, MainActivity.class);
            intent.putExtra("admin1","1");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else if(ref.equals("admin2")){
            intent=new Intent(this, MessagingChat.class);
            String no=remoteMessage.getData().get("no");
            String name=title;
            String cmpID = remoteMessage.getData().get("cmpID");
            intent.putExtra("no",no);
            intent.putExtra("name",name);
            intent.putExtra("cmpID",cmpID);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pIntent=PendingIntent.getActivity(this,92727 , intent,PendingIntent.FLAG_ONE_SHOT );
        Uri soundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoAndAboveNotification notification1=new OreoAndAboveNotification(this);
        Notification.Builder builder=notification1.getNotifictions(title,body,pIntent,soundUri,ref,subText);

        notification1.getManager().notify(92727,builder.build());

    }
}
