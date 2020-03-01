package com.easysolutions.dod_admin;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL_ID = "DOD-OTP SERVICE";
    //public static final String CHANNEL_ID1 = "DOD-OTP Sent";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "DOD-OTP Service",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
//            NotificationChannel serviceChanne2 = new NotificationChannel(
//                    CHANNEL_ID1,
//                    "DOD-OTP Sent",
//                    NotificationManager.IMPORTANCE_NONE
//            );
//            manager.createNotificationChannel(serviceChanne2);
        }
    }

}
