package com.example.ag6505.sharedpreferences;

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
    private int imageResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents(savedInstanceState);
        registerListeners();
    }

    private void initComponents(Bundle bundle) {
        tvActivity = (TextView) findViewById(R.id.tvActivity);
        etActivity = (EditText) findViewById(R.id.etActivity);
        ivActivity = (ImageView) findViewById(R.id.ivActivity);
        btnActivity = (Button) findViewById(R.id.btnActivity);
        if (bundle != null) {
            tvActivity.setText(bundle.getString("tvActivity"));
            setImage(bundle.getInt("ivActivity"));
            btnActivity.setText(bundle.getString("btnActivity"));
        }
    }

    private void registerListeners() {
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvActivity.setText("Changed text");
                etActivity.setText("Changed text");
                setImage(R.drawable.paperleft);
                btnActivity.setText("Done");
            }
        });
    }

    private void setImage(int imageResource) {
        this.imageResource = imageResource;
        ivActivity.setImageResource(imageResource);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tvActivity",tvActivity.getText().toString());
        outState.putInt("ivActivity", imageResource);
        outState.putString("btnActivity",btnActivity.getText().toString());
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
