package com.antonhellbegmail.assignment2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-29.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    ArrayList<Group> groupList;

    public void setGroups(ArrayList<Group> groupList){
        this.groupList = groupList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvGroupName.setText(groupList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView rv;
        TextView tvGroupName;


        public ViewHolder(View itemView) {
            super(itemView);

            tvGroupName = (TextView) itemView.findViewById(R.id.tvGroupName);
        }
    }
}
