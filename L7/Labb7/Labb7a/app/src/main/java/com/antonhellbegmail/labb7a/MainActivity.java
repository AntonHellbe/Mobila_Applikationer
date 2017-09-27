package com.antonhellbegmail.labb7a;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView ivMemory;
    private TextView tvText;
    private JsonTable jsonTable;
    private XmlTable xmlTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivMemory = (ImageView)findViewById(R.id.imageView1);
        tvText = (TextView)findViewById(R.id.textView1);
        jsonTable = (JsonTable)getFragmentManager().findFragmentById(R.id.table_fragment);
        xmlTable = (XmlTable)getFragmentManager().findFragmentById(R.id.xml_table);
        if(NetworkStatus.isOnline(this)) {
//            json("utgifter");
//            json("inkomster");
//			jsonWithReader("Reader");
//            xml();
			textAndImage();
        }
    }

    private void json(String type) {
        jsonTable.setUrlTypeEncoding("http://ddwap.mah.se/tsroax/expendituresjson.php", type, "UTF-8");
    }

    private void jsonWithReader(String type) {
        jsonTable.setUrlTypeEncoding("https://dl.dropboxusercontent.com/u/1074349/data.txt", type, "UTF-8");
    }

    private void xml() {
        xmlTable.setUrlAndEncoding("http://ddwap.mah.se/tsroax/expendituresxml.xml", "UTF-8");
    }

    private void textAndImage() {
        new LoadBitmap("http://i1.kym-cdn.com/photos/images/newsfeed/000/755/142/348.png", this, new BL());
        new LoadText2(new LoadText2.TextListener() {
            @Override
            public void textLoaded(String str) {
                tvText.setText(str);
            }

        },"http://ddwap.mah.se/tsroax/memory/Memory.html","ISO-8859-1", this);
    }

    private class BL implements LoadBitmap.BitmapListener {
        public void bitmapLoaded(Bitmap bitmap) {
            ivMemory.setImageBitmap(bitmap);
        }
    }
}
