package com.antonhellbegmail.assignment2;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-29.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private ArrayList<String> groupList;
    private GroupFragment groupFragment;



    public GroupAdapter(GroupFragment groupFragment){
        this.groupFragment = groupFragment;
        groupList = new ArrayList<>();
    }


    public void setGroups(ArrayList<String> groupList){
        this.groupList = groupList;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvGroupName.setText(groupList.get(position));


    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        RecyclerView rv;
        TextView tvGroupName;



        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvGroupName = (TextView) itemView.findViewById(R.id.tvGroupName);
        }

        @Override
        public void onClick(View view) {
            ((MainActivity)groupFragment.getActivity()).getController().setGroupFragment(tvGroupName.getText().toString());
        }
    }


}
