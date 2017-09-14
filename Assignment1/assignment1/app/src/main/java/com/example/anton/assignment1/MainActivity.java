package com.example.anton.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnSignup;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponenets();


    }

    private void initComponenets() {
        this.etUsername = (EditText) findViewById(R.id.etUsername);
        this.etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignUp);
        controller = new Controller();
        registerListeners();

    }

    private void registerListeners() {
        btnLogin.setOnClickListener(new buttonChoiceListener());
        btnSignup.setOnClickListener(new buttonChoiceListener());
    }

    private class buttonChoiceListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnLogin:
                    if(controller.login("hej", "hejhej")){
                        startIntent(etUsername.getText().toString(), etPassword.getText().toString());
                    }
                    break;
                case R.id.btnSignUp:
                    controller.signUp();
                    break;
            }
        }
    }

    public void startIntent(String userId, String password){
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("userid", userId);
        intent.putExtra("password", password);
        startActivity(intent);
    }


}
