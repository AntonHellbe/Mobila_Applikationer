package com.antonhellbegmail.assignment2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-29.
 */

public class CommService extends Service {

    private String ip = "195.178.227.53";
    private int connectionPort = 7117;
    private Socket socket;
    private RunOnThread thread;
    private Buffer<String> recieveBuffer;
    private DataInputStream input;
    private DataOutputStream output;
    private InetAddress address;
    private Exception exception;
    private Recieve recieve;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        thread = new RunOnThread();
        recieveBuffer = new Buffer<>();
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocalService();
    }

    public void connect(){
        thread.start();
        thread.execute(new Connect());
    }

    public void disconnect() {
        thread.execute(new Disconnect());
    }

    public void send(JSONObject jsonObject) {
        thread.execute(new Send(jsonObject));
    }

    public String recieve() throws InterruptedException{
        return recieveBuffer.get();
    }

    public Exception getException(){
        Exception result = exception;
        exception = null;
        return result;
    }

    public class LocalService extends Binder {

        public CommService getService(){
            return CommService.this;
        }

    }


    private class Recieve extends Thread{

        @Override
        public void run() {
            String result;
            try{
                while(recieve != null){
                    result = input.readUTF();
                    Log.d("RECIEVED FOLLOWING", result);
                    recieveBuffer.put(result);
                }
            }catch(Exception e){
                recieve = null;
            }
        }
    }

    private class Connect implements Runnable{

        @Override
        public void run() {
            try{
                address = InetAddress.getByName(ip);
                socket = new Socket(address, connectionPort);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                output.flush();
                recieveBuffer.put("CONNECTED");
                recieve = new Recieve();
                recieve.start();
            }catch (Exception e){
                exception = e;
                Log.d("EXCEPTION", e.toString());
                recieveBuffer.put("EXCEPTION");
            }
        }
    }

    private class Disconnect implements Runnable{
        @Override
        public void run() {
            try{
                if (input != null)
                    input.close();
                if (output != null)
                    output.close();
                if(socket != null)
                    socket.close();
                thread.stop();
                recieveBuffer.put("CLOSED");
                Log.d("DISCONNECT", "DISCONNECTED");
            }catch(IOException e){
                exception = e;
                recieveBuffer.put("EXCEPTION");
            }
        }
    }

    private class Send implements Runnable{
        private JSONObject jsonObject;

        public Send(JSONObject jsonObject){
            this.jsonObject = jsonObject;
        }
        @Override
        public void run() {
            try {
                output.writeUTF(jsonObject.toString());
                output.flush();
            }catch(IOException e){
                exception = e;
                recieveBuffer.put("EXCEPTION");
            }
        }
    }
}
