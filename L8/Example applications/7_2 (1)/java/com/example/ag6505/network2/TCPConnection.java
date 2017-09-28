package com.example.ag6505.network2;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import f8.Expression;

/**
 * Created by tsroax on 2014-09-30.
 */

public class TCPConnection {
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

    public TCPConnection(String ip, int connectionPort, ReceiveListener listener) {
        this.ip = ip;
        this.connectionPort = connectionPort;
        this.listener = listener;
        thread = new RunOnThread();

    }

    public void connect() {
        thread.start();
        thread.execute(new Connect());
    }

    public void disconnect() {
        thread.execute(new Disconnect());
    }

    public void send(Expression expression) {
        thread.execute(new Send(expression));
    }

    private class Receive extends Thread {
        public void run() {
            String result;
            try {
                while (receive != null) {
                    result = (String) input.readObject();
                    listener.newMessage(result);
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
                Log.d("TCPConnection","Connect-run");
                address = InetAddress.getByName(ip);
                Log.d("TCPConnection-Connect","Skapar socket");
                socket = new Socket(address, connectionPort);
                input = new ObjectInputStream(socket.getInputStream());
                output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
                Log.d("TCPConnection-Connect","Strömmar klara");
                listener.newMessage("CONNECTED");
                receive = new Receive();
                receive.start();
            } catch (Exception e) { // SocketException, UnknownHostException
                Log.d("TCPConnection-Connect",e.toString());
                exception = e;
                listener.newMessage("EXCEPTION");
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
                listener.newMessage("CLOSED");
            } catch(IOException e) {
                exception = e;
                listener.newMessage("EXCEPTION");
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
                listener.newMessage("EXCEPTION");
            }
        }
    }

}
