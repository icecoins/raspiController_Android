package com.socketforpy;

import android.app.Activity;
import android.graphics.RectF;

import androidx.appcompat.app.AppCompatActivity;


/**
 This class is used as a global variable

 * */
public class GlobalInformation {
    //width and height of the screen
    public static int width, height;

    public static int streamPort;
    public static String IP;

    // ratio,   The aspect ratio of screen
    // offsetX, offsetY,    The offset between the click point of the left joystick and the center of the circle
    // offsetX2, offsetY2,
    // R = 144.2f,  Radius of the big circle at the bottom
    public static float ratio, offsetX, offsetY, offsetX2, offsetY2, R = 144.2f;

    //Record the activity of the main work
    public static AppCompatActivity main;

    //joystick touchable area
    public static RectF rect =new RectF();

    //The transparency of the touch area is 0-255.0.
    // In order to test, we first set it to 5
    public static int ontouchAlpha = 5;

    //the final data will be sent.
    // they will be calculated and sent in the DataSendThread
    public static float engineLeft1, engineRight1,
            engineLeft2, engineRight2;
}
