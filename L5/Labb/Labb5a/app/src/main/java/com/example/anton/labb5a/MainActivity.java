package com.example.anton.labb5a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView tvActivity;
    private EditText etActivity;
    private ImageView ivActivity;
    private Button btnActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        registerListeners();
    }

    private void initComponents() {
        tvActivity = (TextView)findViewById(R.id.tvActivity);
        etActivity = (EditText)findViewById(R.id.etActivity);
        ivActivity = (ImageView)findViewById(R.id.ivActivity);
        btnActivity = (Button)findViewById(R.id.btnActivity);
    }

    private void registerListeners() {
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvActivity.setText("Changed text");
                etActivity.setText("Changed text");
                ivActivity.setImageResource(R.drawable.paperleft);
                btnActivity.setText("Done");
            }
        });
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null) this.ivActivity.setImageResource(savedInstanceState.getInt("image"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putInt("image", R.drawable.paperleft);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

