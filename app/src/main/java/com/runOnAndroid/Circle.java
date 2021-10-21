package com.runOnAndroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 Circle class
 * */
public class Circle {

    //Coordinates of the large circle when pressed
    private float x1, y1;

    //Coordinates of the little circle when moved
    private float x2, y2;

    //Radius of circles
    private final float r1, r2;

    //Angle from x1y1 to x2y2
    public float angle;

    //whether screen is pressed
    public boolean isDown = false;

    //whether the small circle is inside the big circle
    public boolean isSmallCircleInside = false;

    //whether the finger moves after pressing
    public boolean move = false;

    //Pictures of big circle and small circle
    public Bitmap circle1, circle2;

    public Circle() {
        //Multiply by scale to fit different screen sizes
        r1 = 480 * 0.4f * GlobalInformation.ratio;
        r2 = 300 * 0.3f * GlobalInformation.ratio;
        //initialize joystick picture
        circle1 = BitmapFactory.decodeResource(GlobalInformation.main.getResources(), R.mipmap.background);
        circle2 = BitmapFactory.decodeResource(GlobalInformation.main.getResources(), R.mipmap.top);
    }
    /**
     Operation after rocker is pressed
     * */
    public void down(float xx, float yy) {
        //Prevent the pressed position from being too close to the edge of the screen
        x1 = Math.max(xx, r1);
        if (GlobalInformation.height - yy < r1){
            y1 = GlobalInformation.height - r1;
        }
        else {
            y1 = yy;
        }
        isDown = true;
    }

    /**
     Operation of moving after pressing the joystick
     * */
    public void move(float xx, float yy) {
        angle = getAngle(xx, yy);
        isSmallCircleInside = in(xx, yy);
        move = isMove(xx, yy);
        if (!isSmallCircleInside) {
            xx = (float) (x1 + Math.sin(angle) * r1 * 0.7f);
            yy = (float) (y1 + Math.cos(angle) * r1 * 0.7f);
        }
        x2 = xx;
        y2 = yy;
    }

    /**
     Operation after loosening
     * */
    public void up() {
        isDown = false;
    }

    /**
     Get the angle from x1y1 to x2y2
     * */
    public float getAngle(float xx, float yy) {
        double angle, k;
        if (y1 == yy){
            if (x1 > xx){
                angle = -Math.PI / 2;
            }
            else{
                angle = Math.PI / 2;
            }
        }
        else {
            //Coordinate slope of two points
            k = (x1 - xx) / (y1 - yy);
            if (y1 > yy) {
                angle = Math.atan(k) + Math.PI;
            } else {
                angle = Math.atan(k);
            }
            //Let the calculated angle belong to - pi / 2 to pi / 2
            if (angle > Math.PI){
                angle -= Math.PI * 2;
            }
            else if (angle < -Math.PI){
                angle += Math.PI * 2;
            }
        }
        return (float) angle;
    }

    /**
     Prevent the small circle from falling off too far,
      and the drag range shall not exceed 70% of r1
     * */
    public boolean in(float xx, float yy) {
        double r = Math.sqrt((x1 - xx) * (x1 - xx) + (y1 - yy) * (y1 - yy));
        return r < r1 * 0.7f;
    }

    /**
     Judge whether to move after pressing the rocker.
      If the distance between x1y1 and x2y2 is greater
      than r1 * 0.15, it is regarded as moving
     * */
    public boolean isMove(float xx, float yy) {
        double r = Math.sqrt((x1 - xx) * (x1 - xx) + (y1 - yy) * (y1 - yy));
        return r > r1 * 0.15f;
    }

    /**
     set the values of offsetX1 and Y1
     * */
    public void setOffsetXY1(){
        GlobalInformation.offsetX = (x2 - x1) / GlobalInformation.R;
        GlobalInformation.offsetY = (y1 - y2) / GlobalInformation.R;
    }

    /**
     set the values of offsetX2 and Y2
     * */
    public void setOffsetXY2(){
        GlobalInformation.offsetX2 = (x2 - x1) / GlobalInformation.R;
        GlobalInformation.offsetY2 = (y1 - y2) / GlobalInformation.R;
    }

    /**
     draw the joystick
     * */
    public void onDraw(Canvas g, Paint p, int flag) {
        //Displayed when the rocker is pressed
        if (isDown) {
            //Transparency of joystick
            p.setAlpha(100);
            GlobalInformation.rect.left = x1 - r1;
            GlobalInformation.rect.top = y1 - r1;
            GlobalInformation.rect.right = x1 + r1;
            GlobalInformation.rect.bottom = y1 + r1;
            g.drawBitmap(circle1, null, GlobalInformation.rect, p);
            GlobalInformation.rect.left = x2 - r2;
            GlobalInformation.rect.top = y2 - r2;
            GlobalInformation.rect.right = x2 + r2;
            GlobalInformation.rect.bottom = y2 + r2;
            g.drawBitmap(circle2, null, GlobalInformation.rect, p);
        }else{
            //clear up the values of offset by flag
            if(flag == 1){
                GlobalInformation.offsetX = 0;
                GlobalInformation.offsetY = 0;
            }else if(flag == 2){
                GlobalInformation.offsetX2 = 0;
                GlobalInformation.offsetY2 = 0;
            }
        }
    }
}