package com.antonhellbegmail.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-29.
 */

public class GroupFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lm;
    private GroupAdapter groupAdapter;
    private SwipeRefreshLayout srlGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.group_fragment, container, false);
        initializeComponents(rootView);
        return rootView;
    }


    private void initializeComponents(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        lm = new LinearLayoutManager(getActivity());
        groupAdapter = new GroupAdapter(this);
        recyclerView.setLayoutManager(lm);
        srlGroup = (SwipeRefreshLayout) rootView.findViewById(R.id.srlGroups);
    }

    public void setAdapter(GroupAdapter groupAdapter){
        recyclerView.setAdapter(groupAdapter);
    }

    @Override
    public void onResume() {
        ((MainActivity)getActivity()).getController().updateGroupFragment(this);
        super.onResume();
    }

    public void updateGroups(ArrayList<String> groupList){
        ((GroupAdapter)recyclerView.getAdapter()).setGroups(groupList);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private class RefreshListener implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
//            ((MainActivity)getActivity()).getController()
        }
    }




}
