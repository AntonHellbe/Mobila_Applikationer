package com.antonhellbegmail.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-29.
 */

public class DataFragment extends Fragment {

    private Boolean bound;
    private Boolean connected = false;
    private Boolean serviceExist = false;
    private String currentUsername = "";
    private String currentGroup = "";
    private int currentFragment = 0;
    private String currentId;


    private ArrayList<Member> currentMemberList = new ArrayList<>();
    private ArrayList<Member> currentPositionList = new ArrayList<>();
    private ArrayList<Group> currentGroupList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Boolean getBound() {
        return bound;
    }

    public void setBound(Boolean bound) {
        this.bound = bound;
    }

    public Boolean getServiceExist() {
        return serviceExist;
    }

    public void setServiceExist(Boolean serviceExist) {
        this.serviceExist = serviceExist;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public int getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(int currentFragment) {
        this.currentFragment = currentFragment;
    }

    public ArrayList<Member> getCurrentMemberList() {
        return currentMemberList;
    }

    public void setCurrentMemberList(ArrayList<Member> currentMemberList) {
        this.currentMemberList = currentMemberList;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public ArrayList<Member> getCurrentPositionList() {
        return currentPositionList;
    }

    public void setCurrentPositionList(ArrayList<Member> currentPositionList) {
        this.currentPositionList = currentPositionList;
    }

    public ArrayList<Group> getCurrentGroupList() {
        return currentGroupList;
    }

    public void setCurrentGroupList(ArrayList<Group> currentGroupList) {
        this.currentGroupList = currentGroupList;
    }

    public void setCurrentGroup(String currentGroup) {
        this.currentGroup = currentGroup;
    }

    public String getCurrentGroup() {
        return currentGroup;
    }
}
