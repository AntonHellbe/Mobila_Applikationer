package com.antonhellbegmail.labb7c;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class TCPConnectionFragment extends Fragment {
    private RunOnThread thread;
    private Receive receive;
    private ReceiveListener listener;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private InetAddress address;
    private int connectionPort;
    private String ip;
    private Exception exception;
    private String status;


    public TCPConnectionFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public void setConnectionPort(int port){
        this.connectionPort = port;
    }

    public void startThread(){
        thread = new RunOnThread();
        status = "CLOSED";
    }

    public void connect() {
        thread.start();
        thread.execute(new Connect());
        status = "CONNECTED";
    }

    public void disconnect() {
        thread.execute(new Disconnect());
        status = "CLOSED";
    }

    public void send(Expression expression) {
        thread.execute(new Send(expression));
    }

    public String getStatus() {
        return status;
    }


    private class Receive extends Thread {
        public void run() {
            String result;
            try {
                while (receive != null) {
                    result = (String) input.readObject();
                    ((MainActivity)getActivity()).getListener().newMessage(result);
                }
            } catch (Exception e) { // IOException, ClassNotFoundException
                receive = null;
            }
        }
    }

    public Exception getException() {
        Exception result = exception;
        exception = null;
        return result;
    }

    private class Connect implements Runnable {
        public void run() {
            try {
                Log.d("TCPConnectionFragment","Connect-run");
                address = InetAddress.getByName(ip);
                Log.d("TCPConnectionFragmen","Skapar socket");
                socket = new Socket(address, connectionPort);
                input = new ObjectInputStream(socket.getInputStream());
                output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
                Log.d("TCPConnectionFragmen","Strömmar klara");
                ((MainActivity)getActivity()).getListener().newMessage("CONNECTED");
                receive = new Receive();
                receive.start();
            } catch (Exception e) { // SocketException, UnknownHostException
                Log.d("TCPConnectionFragmen",e.toString());
                exception = e;
                ((MainActivity)getActivity()).getListener().newMessage("EXCEPTION");
            }
        }
    }

    public class Disconnect implements Runnable {
        public void run() {
            try {
                if (socket != null)
                    socket.close();
                if (input != null)
                    input.close();
                if (output != null)
                    output.close();
                thread.stop();
                ((MainActivity)getActivity()).getListener().newMessage("CLOSED");
            } catch(IOException e) {
                exception = e;
                    ((MainActivity)getActivity()).getListener().newMessage("EXCEPTION");
            }
        }
    }

    public class Send implements Runnable {
        private Expression exp;

        public Send(Expression exp) {
            this.exp = exp;
        }

        public void run() {
            try {
                output.writeObject(exp);
                output.flush();
            } catch (IOException e) {
                exception = e;
                ((MainActivity)getActivity()).getListener().newMessage("EXCEPTION");
            }
        }
    }

}
