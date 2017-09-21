package com.example.anton.assignment1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Anton on 2017-09-16.
 */

public class FragmentLogin extends Fragment {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnSignup;
    private Controller controller;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void registerListeners() {
        btnLogin.setOnClickListener(new ButtonChoiceListener());
        btnSignup.setOnClickListener(new ButtonChoiceListener());
    }


    @Override
    public void onResume() {
        super.onResume();
        controller.restoreLoginInformation();
    }

    private void initializeComponents(View rootView) {
        this.etUsername = (EditText) rootView.findViewById(R.id.etUsername);
        this.etPassword = (EditText) rootView.findViewById(R.id.etPassword);
        this.btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        this.btnSignup = (Button) rootView.findViewById(R.id.btnSignUp);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class ButtonChoiceListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnLogin:
                    if(controller.login()){
                        controller.startUserActivity();
                    }else{
                        Toast.makeText(getActivity(), "Username or password wrong", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnSignUp:
                    controller.swapMainFragment(1);
                    break;
            }
        }
    }

    public String getEtUsername(){
        return this.etUsername.getText().toString();
    }

    public String getEtPassword(){
        return this.etPassword.getText().toString();
    }

    public void setEtUsername(String text) {
        this.etUsername.setText(text);
    }

    public void setEtPassword(String text){
        this.etPassword.setText(text);
    }
}
