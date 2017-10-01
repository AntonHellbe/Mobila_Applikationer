package com.antonhellbegmail.assignment2;

import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-29.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private ArrayList<Member> memberList;

    public MemberAdapter(){
        memberList = new ArrayList<>();
    }

    public void setMemberList(ArrayList<Member> memberList){
        this.memberList = memberList;
        notifyDataSetChanged();
    }
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MemberAdapter.ViewHolder holder, int position) {
        holder.tvGroupName.setText(memberList.get(position).getName());
        holder.tvType.setText("Member name:");

    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView rv;
        TextView tvGroupName, tvType;


        public ViewHolder(View itemView) {
            super(itemView);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
            tvGroupName = (TextView) itemView.findViewById(R.id.tvGroupName);
        }
    }
}
