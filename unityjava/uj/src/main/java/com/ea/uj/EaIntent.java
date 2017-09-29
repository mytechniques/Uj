package com.ea.uj;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;


public class EaIntent   {
    public static final  String defaultSubject ="#SHARE";
    public static final String defaultNotificationTitle = "#Notification";
    public static final String defaultNotificationText = "#This is default notification";

    private static EaIntent instance;
    public static  EaIntent instance() {
        if(instance == null)
            instance = new EaIntent();

        return  instance ;
    }

    private Intent getIntent(String shareText,String type){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,shareText);
        intent.putExtra(Intent.EXTRA_PACKAGE_NAME,"com.ea.unity");
        intent.setType(type);
        return intent;
    }
    public void ShareText(Activity activity,String shareText){
            ShareText(activity,shareText,defaultSubject);
    }
    public void ShareText(Activity activity,String shareText,String subject){
        Intent iShare = getIntent(shareText,"text/plain");
        activity.startActivity(Intent.createChooser(iShare,subject));
    }
    public void ShareImage(Activity activity,String shareText,String imgPath){
        ShareImage(activity,shareText,defaultSubject,imgPath);
    }
    public void ShareImage(Activity activity,String shareText,String subject,String imgPath){
            Intent iShare = getIntent(shareText,"image/jpeg");
            imgPath = imgPath.contains("file://") ? imgPath :"file://"+ imgPath;
            Uri uri = Uri.parse(imgPath);
            iShare.putExtra(Intent.EXTRA_STREAM,uri);
            activity.startActivity(Intent.createChooser(iShare,subject));

    }
    public void SendMail(Activity activity,String subject,String mailText,String [] emails){
        Intent iMail = getIntent(mailText,"message/rfc822");
        iMail.putExtra(Intent.EXTRA_EMAIL,emails);
        iMail.putExtra(Intent.EXTRA_SUBJECT,subject);
        activity.startActivity(iMail);
    }
    public void PushNotification(Activity activity){
        PushNotification(activity,defaultNotificationTitle,defaultNotificationText);}
    public void PushNotification(Activity activity,String title,String msg){
        PendingIntent iClick = PendingIntent.getActivity(activity,0, new Intent(activity,activity.getClass()),0);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(activity).
                setVibrate(new long[]{200}).
                setContentText(msg).
                setContentTitle(title).
                setContentIntent(iClick).
                setSound(Settings.System.DEFAULT_NOTIFICATION_URI).
                setAutoCancel(true).
                setSmallIcon(R.mipmap.ic_launcher);
        NotificationManager nManager = (NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification iNotify = nBuilder.build();



        nManager.notify(0,iNotify);
    }
    public void PushNotification(Activity activity,String title,String msg){
        PendingIntent iClick = PendingIntent.getActivity(activity,0, new Intent(activity,activity.getClass()),0);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(activity).
                setVibrate(new long[]{200}).
                setContentText(msg).
                setContentTitle(title).
                setContentIntent(iClick).
                setSound(Settings.System.DEFAULT_NOTIFICATION_URI).
                setAutoCancel(true).
                setSmallIcon(R.mipmap.ic_launcher);
        NotificationManager nManager = (NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification iNotify = nBuilder.build();



        nManager.notify(0,iNotify);
    }

}
