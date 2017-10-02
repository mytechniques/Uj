package com.ea.uj;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.InputStream;
import java.io.IOException;


public class EaIntent   {
    public static final  String defaultSubject ="#SHARE";
    public static final String defaultNotificationTitle = "#Notification";
    public static final String defaultNotificationText = "#This is default notification";
    public   Activity activity;
    public static EaIntent instance;
    public static  EaIntent instance(Activity activity) {
        if(instance == null){
            instance = new EaIntent();
            instance.activity   = activity;
        }
        Intent service = new Intent(instance.activity,EaService.class);
        instance.activity.startService(service);
        Toast.makeText(EaIntent.instance.activity,"Start Services",Toast.LENGTH_LONG);
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
    public void ShareText( String shareText){
            ShareText(shareText,defaultSubject);
    }
    public void ShareText( String shareText,String subject){
        Intent iShare = getIntent(shareText,"text/plain");
        activity.startActivity(Intent.createChooser(iShare,subject));
    }
    public void ShareImage( String shareText,String imgPath){
        ShareImage(shareText,defaultSubject,imgPath);
    }
    public void ShareImage( String shareText,String subject,String imgPath){
            Intent iShare = getIntent(shareText,"image/jpeg");
            imgPath = imgPath.contains("file://") ? imgPath :"file://"+ imgPath;
            Uri uri = Uri.parse(imgPath);
            iShare.putExtra(Intent.EXTRA_STREAM,uri);
            activity.startActivity(Intent.createChooser(iShare,subject));

    }
    public void SendMail( String subject,String mailText,String [] emails){
        Intent iMail = getIntent(mailText,"message/rfc822");
        iMail.putExtra(Intent.EXTRA_EMAIL,emails);
        iMail.putExtra(Intent.EXTRA_SUBJECT,subject);
        activity.startActivity(iMail);
    }
    public void PushNotification(){
        PushNotification(defaultNotificationTitle,defaultNotificationText);}
    public void PushNotification(String title,String msg) {

        PendingIntent iClick = PendingIntent.getActivity(activity,0, new Intent(activity,activity.getClass()),0);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(activity).
                setVibrate(new long[]{200}).
                setContentText(msg).
                setContentTitle(title).
                setContentIntent(iClick).
                setSound(Settings.System.DEFAULT_NOTIFICATION_URI).
                setAutoCancel(true).
                setSmallIcon(R.mipmap.icon);
        NotificationManager nManager = (NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification iNotify = nBuilder.build();



        nManager.notify(0,iNotify);
    }
    public Bitmap getBitmapFromAssets(String fileName) {
        Bitmap bitmap = null;
        try{
            InputStream istr = activity.getAssets().open(fileName);
            bitmap  = BitmapFactory.decodeStream(istr);
            istr.close();
        }catch(IOException e) { }
        return bitmap;
    }
}
