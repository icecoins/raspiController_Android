package com.runOnAndroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.webkit.WebView;
import android.widget.RelativeLayout;

//extends relativelayout to implement runnable interface
public class CustomLayout extends RelativeLayout implements Runnable{

    private final Paint paint;
    private final Circle circle =new Circle();//joystick
    private final Circle circle2 =new Circle();
    @SuppressLint("ClickableViewAccessibility")
    public CustomLayout(Context context) {
        super(context);
        paint =new Paint();
        WebView wbv = new WebView(context);
        //set OnTouchListener return true to disabled the click event
        wbv.setOnTouchListener((v, event) -> true);

        //setAllowFileAccess as true to access local files (index.html)
        wbv.getSettings().setAllowFileAccess(true);

        //Override the functions in WebViewClient to reach custom html page
        wbv.setWebViewClient(new CustomWebViewClient());

        //try to load the video stream url
        wbv.loadUrl("http://" + GlobalInformation.IP +":"+ GlobalInformation.streamPort +"/?action=stream");
        //wbv.loadUrl("https://baidu.com");

        //add webview to custom layout
        addView(wbv);

        //You're not supposed to delete the sentence below :)
        setBackgroundColor(Color.BLACK);//背景颜色

        //get an OnTouchMove
        OnTouchMove onTouchMove=new OnTouchMove(context, circle);
        //add OnTouchMove to custom layout with definite width and height
        addView(onTouchMove, GlobalInformation.width /3, GlobalInformation.height /2);
        //set the location of onTouchMove
        onTouchMove.setX(0);
        onTouchMove.setY(GlobalInformation.height >> 1);

        //add another onTouchMove
        OnTouchMove onTouchMove2=new OnTouchMove(context, circle2);

        addView(onTouchMove2, GlobalInformation.width /3, GlobalInformation.height /2);

        onTouchMove2.setX(GlobalInformation.width /1.5f);

        onTouchMove2.setY(GlobalInformation.height >> 1);

        //start redraw thread
        new Thread(this).start();
    }
    /**
     Override the function in RelativeLayout
     * */
    @Override
    public void draw(Canvas g) {
        super.draw(g);
        //refresh the
        circle.setOffsetXY1();
        circle2.setOffsetXY2();
        //redraw the circles with layout
        //and the flag show which circle it is
        circle.onDraw(g, paint, 1);
        circle2.onDraw(g, paint, 2);
    }
    /**
     redraw per 20ms
     * */
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(20);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            //execute redraw
            postInvalidate();
        }
    }
}