package com.antonhellbegmail.assignment2;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class SendImageTask extends AsyncTask<String, Integer, Boolean> {

    private byte[] byteArray;
    private SendImageListener listener;

    private byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public SendImageTask(Bitmap bitmap) {
        byteArray = bitmapToByte(bitmap);

    }

    @Override
    protected Boolean doInBackground(String... params) {
        String imageid = params[0];
        String port = params[1];
        String ip = params[2];
        Boolean result;

        try {
            int uploadPort = Integer.parseInt(port);
            InetAddress address = InetAddress.getByName(ip);
            Socket socket = new Socket(address, uploadPort);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            output.writeUTF(imageid);
            output.flush();
            output.writeObject(byteArray);
            output.flush();
            output.close();
            socket.close();
            result = true;
        }catch(IOException e){
            result = false;
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d("SENDIMAGERESULT ", "RESULT: " + result);
        super.onPostExecute(result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public interface SendImageListener{
        public void taskLoaded(String result);
    }

}
