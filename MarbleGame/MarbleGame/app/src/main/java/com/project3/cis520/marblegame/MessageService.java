package com.project3.cis520.marblegame;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import java.util.ServiceConfigurationError;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cadel_000 on 12/16/2014.
 */
public class MessageService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Timer timer;
    public Context ctx;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public void onCreate() {
        super.onCreate();
        ctx = this;
        startService();
    }

    public void startService() {
        timer = new Timer();
        timer.schedule(new mainTask(), 0, 10);
    }


    private class mainTask extends TimerTask {
        public void run() {
            toastHandler.sendEmptyMessage(0);
        }
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }


    private final Handler toastHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "You win!!", Toast.LENGTH_LONG).show();
        }
    };

}