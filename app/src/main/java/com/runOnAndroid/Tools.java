package com.runOnAndroid;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.view.View;
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

        //hide virtual keys
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_IMMERSIVE
                        |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        //hide top status bar
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

    }


}
