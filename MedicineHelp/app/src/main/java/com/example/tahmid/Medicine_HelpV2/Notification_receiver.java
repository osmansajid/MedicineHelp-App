package com.example.tahmid.Medicine_HelpV2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class Notification_receiver extends BroadcastReceiver{
    String docname="";
    String cnt="10";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras!=null){
            docname=extras.getString("DOC");
            cnt=extras.getString("CNT");
        }
        int count=Integer.parseInt(cnt);


        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "SAJID",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("THIS_APP_IS_FREE");
            notificationManager.createNotificationChannel(channel);
        }
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.doctor)
                .setContentTitle("You have an appointment today!!")
                .setContentText("Visit "+docname+".")
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.RED, 3000, 3000)
                .setSound(alarmSound)
                .setAutoCancel(true);
        if (intent.getAction().equals("MY_NOTIFICATION_MESSAGE")) {
            notificationManager.notify(count,builder.build());
        }

    }
}
