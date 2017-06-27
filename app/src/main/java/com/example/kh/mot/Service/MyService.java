package com.example.kh.mot.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by kh on 6/27/2017.
 */

public class MyService extends Service {
    private static final String TAG =MyService.class.getSimpleName() ;
    private boolean isRandomNumberGeneratorOn;
    private int randomNumber;
    private final int MAX = 100;
    private final int MIN = 1;
    private static final int GET_COUNT = 0;

      class MyHandler extends Handler{
          @Override
          public void handleMessage(Message msg) {
              super.handleMessage(msg);
              switch (msg.what){
                  case GET_COUNT:
                      Message message = Message.obtain(null, GET_COUNT);
                      try {
                          message.arg1 = getRandomNumber();
                          msg.replyTo.send(message);
                      } catch (RemoteException e) {
                          e.printStackTrace();
                      }
              }
          }
      }

    Messenger messenger = new Messenger(new MyHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (intent.getPackage().equals("com.example.kh.hai")){
            Toast.makeText(this, "correct package", Toast.LENGTH_SHORT).show();
            return messenger.getBinder();
        }
        else {
            Toast.makeText(this, "wrong package", Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }).start();

        return START_STICKY;
    }

    public int getRandomNumber(){
        return randomNumber;
    }


    public void start(){
        isRandomNumberGeneratorOn = true;
        while(isRandomNumberGeneratorOn){
            try {
                Thread.sleep(2000);
                if(isRandomNumberGeneratorOn){
                    randomNumber = new Random().nextInt(MAX)+MIN;
                    Log.i(TAG, "start: "+randomNumber);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        isRandomNumberGeneratorOn = false;
        Log.i(TAG, "stop: ");
    }

}
