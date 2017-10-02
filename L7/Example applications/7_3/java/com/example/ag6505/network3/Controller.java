package com.example.ag6505.network3;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import f8.Expression;

/**
 * Created by tsroax on 2014-09-30.
 */
public class Controller {
    private MainActivity activity;
    private MainFragment mainFragment;
    private TCPConnection connection;
    private boolean connected = false;
    private ServiceConnection serviceConn;
    private Listener listener;
    private boolean bound = false;

    public Controller(MainActivity activity, MainFragment mainFragment, Bundle savedInstanceState) {
        this.activity = activity;
        this.mainFragment = mainFragment;
        mainFragment.setController(this);
        Intent intent = new Intent(activity, TCPConnection.class);
        intent.putExtra(TCPConnection.IP, "195.178.227.53");
        intent.putExtra(TCPConnection.PORT, "9384");
        if(savedInstanceState==null)
            activity.startService(intent);
        else
            connected = savedInstanceState.getBoolean("CONNECTED",false);
        serviceConn = new ServiceConn();
        boolean result = activity.bindService(intent, serviceConn, 0);
        if (!result)
            Log.d("Controller-constructor", "No binding");
    }

    public void onResume() {
        if(connected) {
            mainFragment.setConnectEnabled(false);
            mainFragment.setDisconnectEnabled(true);
            mainFragment.setSendEnabled(true);
        }
    }

    public void onDestroy() {
        if (bound) {
            activity.unbindService(serviceConn);
            listener.stopListener();
            bound = false;
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("CONNECTED", connected);
    }

    public void connectClicked() {
        connection.connect();
    }

    public void disconnectClicked() {
        if(connected) {
            connection.disconnect();
        }
    }

    public void sendClicked() {
        char operation;
        try {
            int number1 = Integer.parseInt(mainFragment.getNbr1());
            int number2 = Integer.parseInt(mainFragment.getNnr2());
            String operStr = mainFragment.getOperation();
            if(operStr.length()>0)
                operation = operStr.charAt(0);
            else
                operation = '?';
            Expression exp = new Expression(number1,operation,number2);
            connection.send(exp);
        }catch(NumberFormatException e) {
            mainFragment.setResult("Bad arguments: " +mainFragment.getNbr1() +
                    ", " + mainFragment.getNnr2());
        }
    }

    private class ServiceConn implements ServiceConnection {
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            TCPConnection.LocalService ls = (TCPConnection.LocalService) binder;
            connection = ls.getService();
            bound = true;
            listener = new Listener();
            listener.start();
        }

        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    }

    private class Listener extends Thread {
        public void stopListener() {
            interrupt();
            listener = null;
        }

        public void run() {
            String message;
            Exception exception;
            while (listener != null) {
                try {
                    message = connection.receive();
                    activity.runOnUiThread(new UpdateUI(message));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    listener = null;
                }
            }
        }
    }

    private class UpdateUI implements Runnable {
        private String message;

        public UpdateUI(String message) {
            this.message = message;
        }

        public void run() {
            Exception exception = connection.getException();
            if ("CONNECTED".equals(message)) {
                mainFragment.setConnectEnabled(false);
                mainFragment.setDisconnectEnabled(true);
                mainFragment.setSendEnabled(true);
                connected = true;
            } else if ("CLOSED".equals(message)) {
                mainFragment.setConnectEnabled(true);
                mainFragment.setDisconnectEnabled(false);
                mainFragment.setSendEnabled(false);
                connected = false;
            } else if ("EXCEPTION".equals(message) && exception != null) {
                message = exception.toString();
            }
            mainFragment.setResult("Resultat: " + message);
        }
    }
}
