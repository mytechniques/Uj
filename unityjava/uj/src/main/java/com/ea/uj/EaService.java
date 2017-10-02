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
       Thread.sleep(intent.getLongExtra("delay",0) * 1000);
       }
       catch (InterruptedException e){Thread.currentThread().interrupt();}
       String title = intent.getStringExtra("title");
       String msg = intent.getStringExtra("msg");
       EaIntent.instance.ShowNotification(title,msg);
   }




}