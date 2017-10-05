package com.antonhellbegmail.assignment2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Anton on 2017-10-04.
 */

public class CustomImageView extends android.support.v7.widget.AppCompatImageView {

    private static String ip = "195.178.227.53";
    private ChatFragment chatFragment;

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (bm != null) {
            bm = getResizedBitmap(bm, 240, 350);
            super.setImageBitmap(bm);
        }
        super.setImageBitmap(bm);

    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


    public void init(String id, String port, ChatFragment chatFragment){
        this.chatFragment = chatFragment;
        byte[] temp = ((MainActivity)chatFragment.getActivity()).getController().getDataFragment().getFromImageMap(id);
        if(temp != null){
            Log.v("PICTUREFROMHASHMAP", "RETRIVED PICTURE FROM HASHMAP!");
            setImageBitmap(BitmapFactory.decodeByteArray(temp, 0, temp.length));

        }else{
            Log.v("FETCHINGPICTUREIPORT", port);
            getImage imGetter = new getImage();
            imGetter.execute(port, id);
        }


    }



    public class getImage extends AsyncTask<String, Integer, Bitmap> {

        private Bitmap map;

        @Override
        protected Bitmap doInBackground(String... strings) {
            String port = strings[0];
            String imageId = strings[1];
            byte[] downloadedArray;
            try{
                int uploadPort = Integer.parseInt(port);
                InetAddress address = InetAddress.getByName(ip);
                Socket socket = new Socket(address, uploadPort);
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                output.flush();
                output.writeUTF(imageId);
                output.flush();
                downloadedArray = (byte[])input.readObject();
                ((MainActivity)chatFragment.getActivity()).getController().getDataFragment().addToImageMap(imageId, downloadedArray);
                map = BitmapFactory.decodeByteArray(downloadedArray, 0, downloadedArray.length);

                socket.close();

            }catch(IOException e){


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return map;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
