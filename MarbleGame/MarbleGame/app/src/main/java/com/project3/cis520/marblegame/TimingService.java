package com.project3.cis520.marblegame;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cadel_000 on 12/16/2014.
 */
public class TimingService extends Service {

    public CountDownTimer cdt;
    public static Timer timer;
    public static Timer endTimer;
    public Context ctx;
    public int count = 0;
    Toast t;

    public void onCreate()
    {
        super.onCreate();
        ctx = this;
        startService();
    }

    private void startService(){
        timer = new Timer();
        timer.schedule(new mainTask(), 0, 10000);
    }

    private class mainTask extends TimerTask {
        public void run(){
            if(count++ == 1) {
                toastHandler.sendEmptyMessage(0);
                timer.schedule(new endActivityTask(), 0, 10000);
            }
        }
    }

    private class endActivityTask extends TimerTask {
        public void run() {
            if(count++ > 2) {
                sendBroadcast(new Intent("kill"));
            }
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        count = 0;
        timer.cancel();
        super.onDestroy();
    }

    private final Handler toastHandler = new Handler(){

        @Override
        public void handleMessage(Message msg){
           Toast.makeText(getApplicationContext(), "You have ten seconds to move!!", Toast.LENGTH_SHORT).show();
        }
    };
}
