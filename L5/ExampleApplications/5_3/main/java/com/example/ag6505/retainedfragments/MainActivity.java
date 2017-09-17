package com.example.ag6505.retainedfragments;

import android.app.Activity;
import android.app.FragmentManager;
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
    private DataFragment dataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDataFragment();
        initComponents(savedInstanceState);
        registerListeners();
    }

    private void initDataFragment() {
        // find the retained fragment on activity restarts
        FragmentManager fm = getFragmentManager();
        dataFragment = (DataFragment) fm.findFragmentByTag("data");
        // create the fragment and data the first time
        if (dataFragment == null) {
            // add the fragment
            dataFragment = new DataFragment();
            fm.beginTransaction().add(dataFragment, "data").commit();
        }
    }

    private void initComponents(Bundle bundle) {
        tvActivity = (TextView) findViewById(R.id.tvActivity);
        etActivity = (EditText) findViewById(R.id.etActivity);
        ivActivity = (ImageView) findViewById(R.id.ivActivity);
        btnActivity = (Button) findViewById(R.id.btnActivity);
        if (bundle != null) {
            tvActivity.setText(dataFragment.getTvActivityStr());
            ivActivity.setImageResource(dataFragment.getImageResource());
            btnActivity.setText(dataFragment.getBtnActivityStr());
        }
    }

    private void registerListeners() {
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTvActivityText("Changed text");
                setImage(R.drawable.paperleft);
                setBtnActivityText("Done");
            }
        });
    }

    private void setImage(int imageResource) {
        dataFragment.setImageResource(imageResource);
        ivActivity.setImageResource(imageResource);
    }

    private void setTvActivityText(String str) {
        dataFragment.setTvActivityStr(str);
        tvActivity.setText(str);
    }

    private void setBtnActivityText(String str) {
        dataFragment.setBtnActivityStr(str);
        btnActivity.setText(str);
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
