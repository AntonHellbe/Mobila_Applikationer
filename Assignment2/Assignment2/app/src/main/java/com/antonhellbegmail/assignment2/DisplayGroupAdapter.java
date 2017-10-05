package com.antonhellbegmail.assignment2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-10-05.
 */

public class DisplayGroupAdapter extends RecyclerView.Adapter<DisplayGroupAdapter.ViewHolder> {

    private ArrayList<Member> memberList;

    public DisplayGroupAdapter(){
        memberList = new ArrayList<>();
    }

    public void setGroupMembers(ArrayList<Member> memberList){
        this.memberList = memberList;
    }

    @Override
    public DisplayGroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_layout, parent, false);
        DisplayGroupAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(DisplayGroupAdapter.ViewHolder holder, int position) {
        holder.tvMemberName.setText("Member name: " + memberList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView rv;
        TextView tvMemberName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMemberName = (TextView) itemView.findViewById(R.id.tvMezName);

        }
    }
}
