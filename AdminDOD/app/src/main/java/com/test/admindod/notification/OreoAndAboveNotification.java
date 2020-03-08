package com.test.admindod.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.test.admindod.R;

public class OreoAndAboveNotification extends ContextWrapper {
    private static final String ID="some_id";
    public static final String NAME="FirebaseAPP";
    private NotificationManager notificationManager;
    public OreoAndAboveNotification(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
             createChannel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(ID, NAME,NotificationManager.IMPORTANCE_HIGH );
        notificationChannel.enableVibration(true);
        notificationChannel.enableLights(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(notificationChannel);
    }

    public NotificationManager getManager(){
        if(notificationManager==null){
            notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return super.getApplicationInfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  Notification.Builder getNotifictions(String title, String body, PendingIntent pIntent, Uri soundUri, String ref,String subtext){
        if(ref.equals("0")){
            return new Notification.Builder(getApplicationContext(),ID)
                    .setContentIntent(pIntent)
                    .setContentTitle(title)
                    .setStyle(new Notification.BigTextStyle()
                            .bigText(body))
                    .setSound(soundUri)
                    .setSubText(subtext)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.icon);

        }
        return new Notification.Builder(getApplicationContext(),ID)
                .setContentIntent(pIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(soundUri)
                .setSubText(subtext)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.icon);
    }

}
