package com.socketforpy;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

/**
 rewrite OnTouchListener and it is responsible
  for monitoring the gestures of the mobile joystick
 233
 * */
public class OnTouchMove extends View implements View.OnTouchListener{
    private com.socketforpy.Circle circle;
    public OnTouchMove(Context context, com.socketforpy.Circle circle) {
        super(context);
        this.circle = circle;

        //set background color as white
        setBackgroundColor(Color.WHITE);
        //Set touch area transparency
        getBackground().setAlpha(GlobalInformation.ontouchAlpha);

        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        //Add getX () and getY() because this view is not distributed in the upper left corner
        final float xx = ev.getX() + getX(), yy = ev.getY() + getY();

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            circle.down(xx, yy);
        }
        circle.move(xx, yy);
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            circle.up();
        }
        return true;
    }
}