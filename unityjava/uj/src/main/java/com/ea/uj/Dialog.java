package com.ea.uj;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


public class Dialog {
    private static Dialog instance;
    private Context cont;
    public static Dialog instance(){
        if(instance == null)instance = new Dialog();
        return instance;
    }
    public void setContext(Context cont){
        instance().cont = cont;
    }
    public void ShowDialog(String msg){
        Toast.makeText(instance().cont,msg, Toast.LENGTH_LONG).show();
    }
    public void ShowDialog(String msg,int duration){
        Toast.makeText(instance().cont,msg, duration).show();
    }
    public void ShowDialog(String msg,int duration,Rect rect){
        Toast t = Toast.makeText(instance().cont,msg,duration);
        int gravity = (rect.x == 1 ? Gravity.LEFT : rect.x == -1 ? Gravity.RIGHT : Gravity.CENTER_HORIZONTAL) |
                (rect.y == 1 ? Gravity.TOP : rect.x == -1 ? Gravity.BOTTOM : Gravity.CENTER_VERTICAL);
        t.setGravity(gravity,rect.width,rect.height);
        t.show();

    }
}
