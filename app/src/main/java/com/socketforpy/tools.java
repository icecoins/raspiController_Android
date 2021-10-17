package com.socketforpy;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class tools {
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
    public static void setFullscreen(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏 隐藏状态栏
    }

}
