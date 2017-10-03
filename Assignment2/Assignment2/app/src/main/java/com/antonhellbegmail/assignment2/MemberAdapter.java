package com.antonhellbegmail.assignment2;

import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-29.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private ArrayList<Member> memberList;
    public MemberFragment memberFragment;

    public MemberAdapter(MemberFragment mem){
        this.memberFragment = mem;
        memberList = new ArrayList<>();
    }

    public void setMemberList(ArrayList<Member> memberList){
        this.memberList = memberList;
        notifyDataSetChanged();
    }
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout2, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MemberAdapter.ViewHolder holder, int position) {
        holder.tvMemberName.setText(memberList.get(position).getName());
        holder.tvHiddenGroup.setText(memberList.get(position).getGroup());
        holder.swDom.setChecked(memberList.get(position).isShowOnMap());

    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener{
        RecyclerView rv;
        TextView tvMemberName, tvHiddenGroup;
        Switch swDom;


        public ViewHolder(View itemView) {
            super(itemView);
            tvMemberName = (TextView) itemView.findViewById(R.id.tvMemberName);
            tvHiddenGroup = (TextView) itemView.findViewById(R.id.tvHiddenGroup);
            swDom = (Switch) itemView.findViewById(R.id.swDom);
            swDom.setOnCheckedChangeListener(this);
        }


        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            ((MainActivity)memberFragment.getActivity()).getController().setShowOnMap(tvHiddenGroup.getText().toString(), tvMemberName.getText().toString(), b);

        }
    }
}
