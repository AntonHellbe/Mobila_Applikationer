package com.example.ag6505.network3;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import f8.Expression;

public class TCPConnection extends Service {
	public static final String IP="IP",PORT="PORT"; //
	private RunOnThread thread;
	private Receive receive;
	private Buffer<String> receiveBuffer; //
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private InetAddress address;
	private int connectionPort;
	private String ip;
	private Exception exception;


	@Override 
	public int onStartCommand(Intent intent, int flags, int startId) {
		this.ip = intent.getStringExtra(IP);
		this.connectionPort = Integer.parseInt(intent.getStringExtra(PORT));
		thread = new RunOnThread();
		receiveBuffer = new Buffer<String>();
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return new LocalService();
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
	
	public String receive() throws InterruptedException {
		return receiveBuffer.get();
	}

    public Exception getException() {
        Exception result = exception;
        exception = null;
        return result;
    }


    public class LocalService extends Binder {
        public TCPConnection getService() {
            return TCPConnection.this;
        }
    }

	private class Receive extends Thread {
		public void run() {
			String result;
			try {
				while (receive != null) {
					result = (String) input.readObject();
					receiveBuffer.put(result);
				}
			} catch (Exception e) { // IOException, ClassNotFoundException
				receive = null;
			}
		}
	}

	private class Connect implements Runnable {
		public void run() {
			try {
				address = InetAddress.getByName(ip);
				socket = new Socket(address, connectionPort);
				input = new ObjectInputStream(socket.getInputStream());
				output = new ObjectOutputStream(socket.getOutputStream());
				output.flush();
                receiveBuffer.put("CONNECTED");
				receive = new Receive();
				receive.start();
			} catch (Exception e) { // SocketException, UnknownHostException
				exception = e;
				receiveBuffer.put("EXCEPTION");
			}
		}
	}

	private class Disconnect implements Runnable {
		public void run() {
			try {
			    if (input != null)
				    input.close();
			    if (output != null)
				    output.close();
			    if (socket != null)
				    socket.close();
			    thread.stop();
			    receiveBuffer.put("CLOSED");
			} catch(IOException e) {
				exception = e;
				receiveBuffer.put("EXCEPTION");
			}
		}
	}

	private class Send implements Runnable {
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
				receiveBuffer.put("EXCEPTION");
			}
		}
	}
}
