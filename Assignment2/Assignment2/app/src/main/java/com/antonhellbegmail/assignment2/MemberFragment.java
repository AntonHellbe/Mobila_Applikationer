package com.antonhellbegmail.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-29.
 */

public class MemberFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lm;
    private MemberAdapter memberAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.member_fragment, container, false);
        initializeComponents(rootView);
        return rootView;
    }

    private void initializeComponents(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        lm = new LinearLayoutManager(getActivity());
        memberAdapter = new MemberAdapter();
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(memberAdapter);
    }

//    public void setAdapter(ArrayList<Member> memberList){
//        Log.v("ADAPTER", "ADAPTER SET " + memberList.size());
//        memberAdapter.setMemberList(memberList);
//        recyclerView.setAdapter(memberAdapter);
//    }

    @Override
    public void onResume() {
//        setAdapter(new ArrayList<Member>());
        ((MainActivity)getActivity()).getController().setMemberAdapter();
        ((MainActivity)getActivity()).getController().getCurrentMemberList();
        super.onResume();
    }

    public void setData(ArrayList<Member> memberList) {
        ((MemberAdapter)recyclerView.getAdapter()).setMemberList(memberList);
    }
}
