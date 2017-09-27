package com.antonhellbegmail.labb7a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoadBitmap extends Thread {
	private BitmapListener listener;
	private String urlParam;
	private MainActivity mainActivity;
	private Bitmap result;

	public LoadBitmap(String urlParam, MainActivity mainActivity, BitmapListener bitmapListener){
		this.urlParam = urlParam;
		this.listener = bitmapListener;
		this.mainActivity = mainActivity;
        start();
	}

	@Override
	public void run() {
        URL url;
        URLConnection connection;
        InputStream input=null;
        try {
            url = new URL(urlParam);
            connection = url.openConnection();
            input = connection.getInputStream();
            result = BitmapFactory.decodeStream(input);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(input!=null)
                try {
                    input.close();
                } catch (IOException e) {}
        }
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.bitmapLoaded(result);
            }
        });
	}

	public interface BitmapListener {
		public void bitmapLoaded(Bitmap bitmap);
	}
}
