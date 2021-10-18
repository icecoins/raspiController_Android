package com.socketforpy;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class newSocket extends AppCompatActivity {
    Socket sc = null;
    String ip = "127.0.0.1";
    int socketPort = 1234, streamPort = 1234;

    //set a TextView to show messages from the server
    private TextView msgList;
    private static int linesOfMsg = 0;

    public static float r1 = 0, r2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_socket);
        tools.setFullscreen(this);

        //use a TextView to show the message from the server
        msgList = new TextView(this);

        //set up some basic values
        GlobalInformation.main=this;
        GlobalInformation.offsetX = 0;
        GlobalInformation.offsetY = 0;
        GlobalInformation.offsetX2 = 0;
        GlobalInformation.offsetY2 = 0;
        GlobalInformation.engineLeft1 = 0;
        GlobalInformation.engineRight1 = 0;
        GlobalInformation.engineLeft2 = 0;
        GlobalInformation.engineRight2 = 0;
        //try to get the ip(String) and ports(int) from previous activity
        if(!getIntent().getStringExtra("IP").equals("")){
            ip = getIntent().getStringExtra("IP");
        }
        if(!getIntent().getStringExtra("SOCKETPORT").equals("")){
            socketPort = Integer.parseInt(getIntent().getStringExtra("SOCKETPORT"));
        }
        if(!getIntent().getStringExtra("STREAMPORT").equals("")){
            streamPort = Integer.parseInt(getIntent().getStringExtra("STREAMPORT"));
        }

        //set the ip in global information
        GlobalInformation.IP = ip;
        GlobalInformation.streamPort = streamPort;

        //start three threads below
        new ConnectionMaintenanceThread().start();
        new DataSendThread().start();
        new DataReceiveThread().start();

        //Obtain the screen resolution and 1920 * 1080 ratio
        // to adapt to different screen sizes
        DisplayMetrics dis = getResources().getDisplayMetrics();
        GlobalInformation.width = dis.widthPixels;
        GlobalInformation.height = dis.heightPixels;
        GlobalInformation.ratio = (float) (Math.sqrt(GlobalInformation.width * GlobalInformation.height)
                / Math.sqrt(1920 * 1080));

        //get a CustomLayout
        CustomLayout layout = new CustomLayout(this);
        //add the message list to the CustomLayout with definite width and height
        layout.addView(msgList, GlobalInformation.width /6, GlobalInformation.height /6);
        //set the location of the message list
        msgList.setX(GlobalInformation.width /1.2f);
        msgList.setY(0);
        //set the text and color of the message list
        msgList.setText("");
        msgList.setTextColor(Color.RED);
        //set the content view as CustomLayout
        setContentView(layout);
    }

    /**
     this thread will always try to check the socket connection's survival per 1.5 seconds,
     and try to reconnect it if it has been down.
     * */
    class ConnectionMaintenanceThread extends Thread{
        @Override
        public void run(){
            while (true){
                if(sc == null){
                    try {
                        sc = new Socket(ip, socketPort);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     this thread will always try to get some data from the server,
     and show it on the screen through the message list
     * */
    class DataReceiveThread extends Thread {
        @SuppressLint("SetTextI18n")
        @Override
        public void run(){
            while (true) {
                try {
                    if(sc != null){
                        while(true){
                            //use BufferedReader to receive and analyse the data
                            BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()), 1024 );
                            String s;
                            //once get any data, try to show it
                            while((s = br.readLine()) != null){
                                //Log.e("msg>>: ", s);
                                String finalS = s;
                                //to change the ui in a child thread must call the function of runOnUiThread
                                runOnUiThread(()->{
                                    //set the max lines of the sentences as 3
                                    if(linesOfMsg >= 2){
                                        msgList.setText(finalS + "\n");
                                        linesOfMsg = 0;
                                    }else{
                                        msgList.append(finalS + "\n");
                                        linesOfMsg++;
                                    }
                                });
                            }
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                    break;
                }
            }
        }
    }

    /**
     * this thread will always try to calculate and send some data to the server per 0.5s
     * */
    class DataSendThread extends Thread {
        /**
         this function try to normalize some values
         offsetX and Y is the offset distance between click point and circle center
         engineRight and Left is the power will be used to control the engine by the server
         x1 y1 and x2 y2 are corresponding to two joysticks respectively
         * */
        public void normalization(){
            float x1 = GlobalInformation.offsetX,
                    y1 = GlobalInformation.offsetY,
                    x2 = GlobalInformation.offsetX2,
                    y2 = GlobalInformation.offsetY2;

            //data below are not necessary
            //r1 = (float) Math.sqrt(x1 * x1 + y1 * y1);
            //r2 = (float) Math.sqrt(x2 * x2 + y2 * y2);

            //it try to get the power of engines through the offset distance
            if(x1 == 0.0f && y1 == 0.0f){
                GlobalInformation.engineRight1 = 0.0f;
                GlobalInformation.engineLeft1 = 0.0f;
            }
            else if(x1 > 0 && y1 > 0){
                GlobalInformation.engineLeft1 = r1;
                GlobalInformation.engineRight1 = (y1 - x1) * r1;
            }
            else if(x1 < 0 && y1 > 0){
                GlobalInformation.engineRight1 = r1;
                GlobalInformation.engineLeft1 = (y1 + x1) * r1;
            }
            else if(x1 > 0 && y1 < 0){
                GlobalInformation.engineLeft1 = - r1;
                GlobalInformation.engineRight1 = (y1 + x1) * r1;
            }
            else if(x1 < 0 && y1 < 0){
                GlobalInformation.engineRight1 = - r1;
                GlobalInformation.engineLeft1 = (y1 - x1) * r1;
            }

            if(x2 == 0.0f && y2 == 0.0f){
                GlobalInformation.engineRight2 = 0.0f;
                GlobalInformation.engineLeft2 = 0.0f;
            }
            else if(x2 > 0 && y2 > 0){
                GlobalInformation.engineLeft2 = r2;
                GlobalInformation.engineRight2 = (y2 - x2) * r2;
            }
            else if(x2 < 0 && y2 > 0){
                GlobalInformation.engineRight2 = r2;
                GlobalInformation.engineLeft2 = (y2 + x2) * r2;
            }
            else if(x2 > 0 && y2 < 0){
                GlobalInformation.engineLeft2 = - r2;
                GlobalInformation.engineRight2 = (y2 + x2) * r2;
            }
            else if(x2 < 0 && y2 < 0){
                GlobalInformation.engineRight2 = - r2;
                GlobalInformation.engineLeft2 = (y2 - x2) * r2;
            }
        }

        @Override
        public void run(){
            //this while true loop will send data per 0.5s
            while (true){
                normalization();
                /* Log.e("msg:", "\t x1: " + (int)(Info.rlaX*100)+" , y1: "+
                        (int)(Info.rlaY*100)
                        + ", r1: " + (int)(r1*100)
                        +"\t x2: "+ (int)(Info.rlaX2*100)+" , y2: "+ (int)(Info.rlaY2*100)
                        + ", r2: " + (int)(r2*100));*/
                if(sc != null){
                    //if socket connection is alive, try to send data
                    PrintWriter ss = null;
                    try {
                        ss = new PrintWriter(sc.getOutputStream() ,true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    PrintWriter finalSs = ss;
                    //use a child thread to send
                    new Thread(()->{
                        finalSs.println(
                                (int)(GlobalInformation.engineLeft1*100)+ " "
                                        + (int)(GlobalInformation.engineRight1*100)+" "
                                        + (int)(GlobalInformation.engineLeft2*100)+ " "
                                        +(int)(GlobalInformation.offsetY2 *100));
                    }).start();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}