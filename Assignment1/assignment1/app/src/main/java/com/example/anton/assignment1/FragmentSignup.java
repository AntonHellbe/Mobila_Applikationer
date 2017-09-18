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

public class FragmentSignup extends Fragment {

    private EditText etUsername, etName, etLastname, etPassword;
    private Button btnRegister, btnBack;
    private Controller controller;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void registerListeners() {
        btnRegister.setOnClickListener(new ButtonListener());
        btnBack.setOnClickListener(new ButtonListener());
    }

    private void initializeComponents(View rootView) {
        etUsername = (EditText) rootView.findViewById(R.id.etNewUsername);
        etPassword = (EditText) rootView.findViewById(R.id.etNewUserPassword);
        etName = (EditText) rootView.findViewById(R.id.etName);
        etLastname = (EditText) rootView.findViewById(R.id.etLastname);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnBack = (Button) rootView.findViewById(R.id.btnBack);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btnRegister:
                    String result = controller.signUp();
                    controller.clearFields();
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                    controller.swapMainFragment(0);
                    break;
                case R.id.btnBack:
                    controller.swapMainFragment(0);
                    break;
            }
        }
    }

    public String getUsername(){
        return etUsername.getText().toString();
    }

    public String getPassword(){
        return etPassword.getText().toString();
    }

    public String getName(){
        return etName.getText().toString();
    }

    public String getLastName(){
        return etLastname.getText().toString();
    }

    public void setEtUsername(String text){
        etUsername.setText(text);
    }

    public void setEtName(String text){
        etName.setText(text);
    }

    public void setEtLastname(String text){
        etLastname.setText(text);
    }

    public void setEtPassword(String text){
        etPassword.setText(text);
    }
}
