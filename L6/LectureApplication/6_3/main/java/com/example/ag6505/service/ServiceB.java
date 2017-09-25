package com.example.ag6505.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Call to startService executes in same thread, in sequence
 */

public class ServiceB extends Service {
    private Thread thread;
    private Buffer<String> buffer;
    private boolean serviceRunning;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceB - onCreate", "");
        buffer = new Buffer<String>();
        thread = new Thread(new MyLogger());
        thread.start();
        serviceRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SB - onStartCommand",(intent==null)?"Null":"Not null");
        if(intent!=null) {
            int times = intent.getIntExtra("times", 0);
            for (int i = 0; i < times; i++) {
                buffer.put("B Nbr " + i);
            }
        }
        return Service.START_REDELIVER_INTENT; // Useful for network communications
    }

    @Override
    public void onDestroy() {
        serviceRunning = false;
        Log.d("ServiceB - onDestroy","Service down");
        super.onDestroy();
    }

    private class MyLogger implements Runnable {
        public void run() {
            while( serviceRunning ) {
                try {
                    Log.d("MyLogger - run",buffer.get());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
