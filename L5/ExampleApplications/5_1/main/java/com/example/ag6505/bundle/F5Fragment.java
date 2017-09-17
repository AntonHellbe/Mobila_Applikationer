package com.example.ag6505.bundle;


import android.os.Bundle;
import android.app.Fragment;
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


    public F5Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt("image", R.drawable.paperleft);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState!= null) this.ivFragment.setImageResource(savedInstanceState.getInt("image"));
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

    private class BL implements View.OnClickListener {
        public void onClick(View v) {
            tvFragment.setText("Changed text");
            etFragment.setText("Changed text");
            ivFragment.setImageResource(R.drawable.paperleft);
            btnFragment.setText("Done");
        }
    }
}
