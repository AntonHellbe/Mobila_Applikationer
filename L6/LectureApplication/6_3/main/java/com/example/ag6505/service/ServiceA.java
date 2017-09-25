package com.example.ag6505.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Every call to startService use separate thread
 */
public class ServiceA extends Service {
    private boolean serviceRunning;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceA - onCreate","");
        serviceRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SA - onStartCommand",(intent==null)?"Null":"Not null");
        if(intent!=null) { // intent null vid återstart vid START_STICKY
            Thread thread = new Thread(new MyLogger(intent));
            thread.start();
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("ServiceA - onDestroy","Service down");
        serviceRunning = false;
        super.onDestroy();
    }

    private class MyLogger implements Runnable {
        private Intent intent;

        public MyLogger(Intent intent) {
            this.intent = intent;
        }

        public void run() {
            int times = intent.getIntExtra("times", 0);
            for (int i = 0; i < times && serviceRunning; i++) {
                Log.d("MyLogger - run","A Nbr " + i);
                try {
                    Thread.sleep(3000);
                } catch(InterruptedException e) {}
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
