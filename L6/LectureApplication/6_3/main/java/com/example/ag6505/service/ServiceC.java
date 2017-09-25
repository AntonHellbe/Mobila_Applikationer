package com.example.ag6505.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class ServiceC extends Service {
    private MyLogger myLogger;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("onStartCommand",(intent==null)?"Null":"Not null");
        if(intent!=null) {
            int times = intent.getIntExtra("times", 0);
            myLogger = new MyLogger();
            myLogger.execute(times);
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("ServiceC - onDestroy","Service down");
        myLogger.cancel(true);
        super.onDestroy();
    }

    private class MyLogger extends AsyncTask<Integer,Integer,Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            int times = params[0];
            for(int i=1; i<=times; i++  ) {
                publishProgress(i);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {}
            }
            return times;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("onProgressUpdate", "C Nbr " + values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Log.d("onPostExecute", "Ready");
            ServiceC.this.stopSelf();
        }

        @Override
        protected void onCancelled(Integer result){
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
