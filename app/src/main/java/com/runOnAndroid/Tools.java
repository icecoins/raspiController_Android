package com.runOnAndroid;

import android.content.Context;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Tools {
    private static Toast t1;
    /**
     show some message on any activity
     * */
    public static void showMsg(Context ct, String s){
        try{
            if(Looper.myLooper() == null){
                Looper.prepare();
                if(t1 == null){
                    t1= Toast.makeText(ct,s, Toast.LENGTH_SHORT);
                }else {
                    t1.cancel();
                    t1.setText(s);
                }
                t1.show();
                Looper.loop();
            }else{
                if(t1 == null){
                    t1= Toast.makeText(ct,s, Toast.LENGTH_SHORT);
                }else{
                    t1.cancel();
                    t1.setText(s);
                }
                t1.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     set the activity to be fullscreen
     * */
    public static void setFullscreen(AppCompatActivity activity) {
        //hide ActionBar
        Objects.requireNonNull(activity.getSupportActionBar()).hide();
        //set to be full screen
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
