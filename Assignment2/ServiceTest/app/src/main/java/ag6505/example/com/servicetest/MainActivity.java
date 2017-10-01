package ag6505.example.com.servicetest;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private Controller controller;
    private TextView tvType, tvGroup, tvId, tvLongitude, tvLatitude, tvMembers;
    private Button btnConnect, btnDisconnect, btnSend;


    @Override
    protected void onDestroy() {
        controller.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        controller.onResume();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = controller.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        registerListeners();
        controller = new Controller(this, savedInstanceState);

    }

    private void registerListeners() {
        btnConnect.setOnClickListener(new ButtonListener());
        btnDisconnect.setOnClickListener(new ButtonListener());
        btnSend.setOnClickListener(new ButtonListener());
    }

    private void initializeComponents() {
        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnDisconnect = (Button) findViewById(R.id.btnDisconnect);
        btnSend = (Button) findViewById(R.id.btnSend);
        tvType = (TextView) findViewById(R.id.tvType);
        tvGroup = (TextView) findViewById(R.id.tvGroup);
        tvId = (TextView) findViewById(R.id.tvId);
        tvMembers = (TextView) findViewById(R.id.tvMembers);
    }


    public void setBtnConnect(boolean active){
        btnConnect.setEnabled(active);
    }

    public void setBtnDisconnect(boolean active){
        btnDisconnect.setEnabled(active);
    }

    public void setBtnSend(boolean btnSend) {
        this.btnSend.setEnabled(btnSend);
    }

    public String getTvType() {
        return tvType.getText().toString();
    }

    public void setTvType(String tvType) {
        this.tvType.setText(tvType);
    }

    public String getTvGroup() {
        return tvGroup.getText().toString();
    }

    public void setTvGroup(String tvGroup) {
        this.tvGroup.append(tvGroup);
    }

    public String getTvId() {
        return tvId.getText().toString();
    }

    public void setTvId(String tvId) {
        this.tvId.setText(tvId);
    }

    public String getTvMembers() {
        return tvMembers.getText().toString();
    }

    public void setTvMembers(String tvMembers) {
        this.tvMembers.append(tvMembers + " ");
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btnConnect:
                    controller.connectClicked();
                    break;
                case R.id.btnDisconnect:
                    controller.disconnectClicked();
                    break;
                case R.id.btnSend:
                    controller.sendClicked();
                    break;
            }
        }
    }
}