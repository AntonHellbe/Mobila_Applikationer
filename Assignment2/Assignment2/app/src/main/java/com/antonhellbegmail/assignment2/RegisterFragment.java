package com.antonhellbegmail.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragment extends Fragment {

    private Button btnRegister;
    private EditText etUsername, etGroup;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_fragment, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void registerListeners() {
        btnRegister.setOnClickListener(new ButtonListener());
    }

    private void initializeComponents(View rootView) {
        btnRegister = rootView.findViewById(R.id.btnRegister);
        etUsername =  rootView.findViewById(R.id.etUsername);
        etGroup = rootView.findViewById(R.id.etGroup);
    }


    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String result;
            switch(view.getId()){
                case R.id.btnRegister:
                    result = ((MainActivity)getActivity()).getController().register(etUsername.getText().toString(), etGroup.getText().toString());
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
