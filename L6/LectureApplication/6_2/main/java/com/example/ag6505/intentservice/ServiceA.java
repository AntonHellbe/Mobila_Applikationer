package com.example.ag6505.intentservice;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ServiceA extends IntentService {
    private boolean serviceRunning;

    public ServiceA() {
        super("ServiceA");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("onStartCommand",(intent==null)?"Null":"Not null");
        serviceRunning = true;
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        Log.d("onDestroy","Service down");
        serviceRunning = false;
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("onHandleIntent","begin");
        int times = intent.getIntExtra("times", 0);
        for(int i=0; i<times && serviceRunning; i++) {
            Log.d("onHandleIntent", "Nbr " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
        Log.d("onHandleIntent","end");
    }
}
