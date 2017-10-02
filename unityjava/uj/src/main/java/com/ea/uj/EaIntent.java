package com.ea.uj;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.widget.Toast;

import java.io.InputStream;
import java.io.IOException;


public class EaIntent   {
    public   Activity activity;

    public static EaIntent instance;
    public static  EaIntent instance(Activity activity) {
        if(instance == null){
            instance = new EaIntent();
            instance.activity   = activity;
        }
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

    public void ShareText(String subject ,String shareText){
        Intent iShare = getIntent(shareText,"text/plain");
        activity.startActivity(Intent.createChooser(iShare,subject));
    }
    public void ShareImage(String subject,String shareText,String imgPath){
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

    public void ShowDialog(String msg){
        Toast.makeText(instance.activity,msg, Toast.LENGTH_LONG).show();
    }
    public void ShowDialog(String msg,int duration){
        Toast.makeText(instance.activity,msg, duration).show();
    }
    public void ShowDialog(String msg,int duration,Rect rect){
        Toast t = Toast.makeText(instance.activity,msg,duration);
        int gravity = (rect.x == 1 ? Gravity.LEFT : rect.x == -1 ? Gravity.RIGHT : Gravity.CENTER_HORIZONTAL) |
                (rect.y == 1 ? Gravity.TOP : rect.x == -1 ? Gravity.BOTTOM : Gravity.CENTER_VERTICAL);
        t.setGravity(gravity,rect.width,rect.height);
        t.show();

    }

    public void PushNotification(String title,String msg,long delay){
        Intent service = new Intent(instance.activity,EaService.class);
        service.putExtra("delay",delay);
        service.putExtra("title",title);
        service.putExtra("msg",msg);
        instance.activity.startService(service);

    }
    public void ShowNotification(String title,String msg) {
        PendingIntent iClick = PendingIntent.getActivity(activity,0, new Intent(activity,activity.getClass()),0);
        Resources res = instance.activity.getResources();
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(activity).
                setVibrate(new long[]{200}).
                setContentText(msg).
                setContentTitle(title).
                setContentIntent(iClick).
                setSound(Settings.System.DEFAULT_NOTIFICATION_URI).
                setAutoCancel(true).
                setSmallIcon(res.getIdentifier("notify_small","drawable",instance.activity.getPackageName())).
                setLargeIcon(getBitmapFromAssets("notify_large.png"));

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
