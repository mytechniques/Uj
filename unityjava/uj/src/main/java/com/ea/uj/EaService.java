package com.ea.uj;
import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

public class EaService extends IntentService{

    public EaService(){
        super("EaService");
    }
   @Override
    public void onHandleIntent(Intent intent){
       try{
       Thread.sleep(5000);
       }
       catch (InterruptedException e){Thread.currentThread().interrupt();}
       ShowNotification();
   }
     public void ShowNotification(){
         EaIntent.instance.PushNotification();
         Toast.makeText(EaIntent.instance.activity,"Notify",Toast.LENGTH_LONG);
     }



}