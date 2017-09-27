package com.antonhellbegmail.labb7a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Anton on 2017-09-27.
 */

public class LoadText2 {

    private String urlParam;
    private String encoding;
    private TextListener textListener;
    private MainActivity mainActivity;

    public LoadText2(TextListener textListener, String url, String encoding, MainActivity mainActivity){
        this.textListener = textListener;
        this.urlParam = url;
        this.encoding = encoding;
        this.mainActivity = mainActivity;
        new Thread(new TextLoader()).start();
    }


    private class TextLoader implements Runnable{
        @Override
        public void run() {
            final StringBuilder result = new StringBuilder();
            BufferedReader reader = null;
            try {
                URL url = new URL(urlParam);
                URLConnection connection = url.openConnection();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
                String txt;
                while((txt=reader.readLine())!=null)
                    result.append(txt+"\n");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch(Exception e) {}
            }
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textListener.textLoaded(result.toString());
                }
            });
        }
    }
    public interface TextListener {
        public void textLoaded(String str);
    }
}
