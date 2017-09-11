package com.antonhellbegmail.labb3b;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Anton on 2017-09-11.
 */

public class InstructionAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater;

    public InstructionAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull String[] objects) {

        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
}
