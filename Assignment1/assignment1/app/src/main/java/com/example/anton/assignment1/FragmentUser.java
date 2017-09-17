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
import android.widget.Toolbar;


/**
 * Created by Anton on 2017-09-12.
 */

public class FragmentUser extends Fragment {

    private EditText etUsername, etPassword, etName, etLastname;
    private Button editBtn;
    private Controller controller;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void registerListeners() {
        editBtn.setOnClickListener(new ButtonListener());
    }

    private void initializeComponents(View rootView) {
        etUsername = (EditText) rootView.findViewById(R.id.etNewUsername);
        etPassword = (EditText) rootView.findViewById(R.id.etNewPassword);
        etName = (EditText) rootView.findViewById(R.id.etNewName);
        etLastname = (EditText) rootView.findViewById(R.id.etNewLastname);
        editBtn = (Button) rootView.findViewById(R.id.btnUpdate);
    }

    public void setTvUsername(String username) {
        etUsername.setHint(username);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.setUserDetails();
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String result = controller.editUser(etUsername.getText().toString(), etName.getText().toString(),
                    etLastname.getText().toString(), etPassword.getText().toString());

            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
        }
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
