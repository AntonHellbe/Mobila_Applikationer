package com.example.ag6505.sharedpreferences;


import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class F5Fragment extends Fragment {
    private TextView tvFragment;
    private EditText etFragment;
    private ImageView ivFragment;
    private Button btnFragment;
    private int imageResource;


    public F5Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f5, container, false);
        initComponents(view);
        registerListeners();
        return view;
    }

    private void initComponents(View view) {
        tvFragment = (TextView)view.findViewById(R.id.tvFragment);
        etFragment = (EditText)view.findViewById(R.id.etFragment);
        ivFragment = (ImageView)view.findViewById(R.id.ivFragment);
        btnFragment = (Button)view.findViewById(R.id.btnFragment);
    }

    private void registerListeners() {
        btnFragment.setOnClickListener(new BL());
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("F5Fragment", Activity.MODE_PRIVATE);
        String tvFragmentText = sharedPreferences.getString("tvFragment",null);
        if(tvFragmentText!=null) {
            tvFragment.setText(tvFragmentText);
            setImage(sharedPreferences.getInt("ivFragment", 0));
            btnFragment.setText(sharedPreferences.getString("btnFragment", ""));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("F5Fragment", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tvFragment",tvFragment.getText().toString());
        editor.putInt("ivFragment", imageResource);
        editor.putString("btnFragment", btnFragment.getText().toString());
        editor.apply();
    }

    private void setImage(int resource) {
        imageResource = resource;
        ivFragment.setImageResource(imageResource);
    }

    private class BL implements View.OnClickListener {
        public void onClick(View v) {
            tvFragment.setText("Changed text");
            etFragment.setText("Changed text");
            setImage(R.drawable.paperleft);
            btnFragment.setText("Done");
        }
    }
}
