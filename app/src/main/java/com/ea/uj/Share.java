package com.ea.uj;
import android.content.Intent;
import android.net.Uri;

import java.util.Objects;

public class Share {
    public static Intent instance;
    public static Intent instance(){
        if(instance == null)
            instance = new Intent();
        return  instance;
    }
    public void Share(String text,String subject){
        subject = subject == "" ? "com.katana.facebook" : subject;
        Intent intent = instance();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        intent.createChooser(intent,subject);
    }
    public void Share(String text,String link,String subject){
        subject = Objects.equals(subject, "") ? "com.katana.facebook" : subject;
        String path =  link.indexOf("file://") != -1 ? ""  : "file://";
        Uri uri = Uri.parse(path + link);
        Intent intent = instance();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        intent.createChooser(intent,subject);
    }

}
