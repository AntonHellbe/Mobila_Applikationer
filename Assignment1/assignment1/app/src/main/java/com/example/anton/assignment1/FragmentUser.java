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
 * Created by Anton on 2017-09-12.
 */

public class FragmentUser extends Fragment {

    private EditText etUsername, etPassword;
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
        controller.updateUsername();
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String result = controller.editUser(etUsername.getText().toString(), etPassword.getText().toString());

            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
        }
    }
}
