package com.antonhellbegmail.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

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
    private String currentId = "";
    private double currentLat;
    private double currentLong;
    private String clickedGroup = "";


    private HashMap<String, ArrayList<Member>> membermap = new HashMap<>();
    private HashMap<String, String> idMap = new HashMap<>();

    private ArrayList<String> myGroups = new ArrayList<>();
    private ArrayList<Member> currentPositionList = new ArrayList<>();
    private ArrayList<String> groupList = new ArrayList<>();
    private ArrayList<Member> clickedGroupList = new ArrayList<>();


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


    public void setCurrentGroup(String currentGroup) {
        this.currentGroup = currentGroup;
    }

    public String getCurrentGroup() {
        return currentGroup;
    }

    public double getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public double getCurrentLong() {
        return currentLong;
    }

    public void setCurrentLong(double currentLong) {
        this.currentLong = currentLong;
    }

    public void addToMap(String group, ArrayList<Member> memberList){
        membermap.put(group, memberList);

    }

    public ArrayList<Member> getFromMap(String groupname){
        return membermap.get(groupname);
    }

    public ArrayList<String> getGroupList() {
        return groupList;
    }

    public void setGroupList(ArrayList<String> groupList) {
        this.groupList = groupList;
    }

    public void addGroup(String group){
        myGroups.add(group);
    }

    public ArrayList<String> getMyGroups(){
        return myGroups;
    }

    public void setClickedGroup(String clickedGroup) {
        this.clickedGroup = clickedGroup;
    }

    public String getClickedGroup(){
        return this.clickedGroup;
    }

    public ArrayList<Member> getClickedGroupList() {
        return clickedGroupList;
    }

    public void setClickedGroupList(ArrayList<Member> clickedGroupList) {
        this.clickedGroupList = clickedGroupList;
    }

    public void addToIdMap(String groupName, String id) {
        idMap.put(groupName, id);
    }

    public String getFromIdMap(String groupname){
        return idMap.get(groupname);
    }
}
