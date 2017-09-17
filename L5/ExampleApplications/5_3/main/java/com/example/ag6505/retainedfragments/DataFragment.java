package com.example.ag6505.retainedfragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment {
    private String tvActivityStr;
    private int imageResource;
    private String btnActivityStr;


    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment (invisible fragment)
        setRetainInstance(true);
    }

    public String getTvActivityStr() {
        return tvActivityStr;
    }

    public void setTvActivityStr(String tvActivityStr) {
        this.tvActivityStr = tvActivityStr;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getBtnActivityStr() {
        return btnActivityStr;
    }

    public void setBtnActivityStr(String btnActivityStr) {
        this.btnActivityStr = btnActivityStr;
    }
}
