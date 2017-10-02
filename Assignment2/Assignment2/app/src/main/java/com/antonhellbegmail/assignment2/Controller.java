package com.antonhellbegmail.assignment2;

import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Controller {

    private android.location.LocationListener locationListener;
    private MainActivity mainActivity;
    private GroupFragment groupFragment;
    private MemberFragment memberFragment;
    private RegisterFragment registerFragment;
    private MapFragment mapFragment;
    private DisplayGroupFragment displayFrag;
    private DataFragment dataFragment;
    private CommService commService;


    private String currentUsername = "";
    private boolean connected = false;
    private ServiceConnection serviceConnection;
    private Listener listener;
    private boolean bound = false;
    private ServerCommands serverCommands;
    private int currentFragment = 0;

    private double currentLat = 0;
    private double currentLong = 0;

    private LocationManager locationManager;

    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.locationListener = new LocList();

        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);

        initDataFragment();
        initComService();

        this.groupFragment = new GroupFragment();
        this.mapFragment = new MapFragment();
        this.memberFragment = new MemberFragment();
        this.registerFragment = new RegisterFragment();
        this.displayFrag = new DisplayGroupFragment();


        serverCommands = new ServerCommands();

        switchFragment(currentFragment);
    }


    public void initDataFragment(){
        FragmentManager fm = mainActivity.getFragmentManager();
        dataFragment = (DataFragment) fm.findFragmentByTag("data");
        if(dataFragment == null){
            dataFragment = new DataFragment();
            fm.beginTransaction().add(dataFragment, "data").commit();
        }
    }

    public void initComService(){
        Intent intent = new Intent(mainActivity, CommService.class);
        if(!dataFragment.getServiceExist()) {
            mainActivity.startService(intent);
            dataFragment.setServiceExist(true);
        }else {
            connected = dataFragment.getConnected();
        }
        serviceConnection = new ServiceConnection();
        boolean result = mainActivity.bindService(intent, serviceConnection, 0);
        if(!result){
            Log.d("Controller-constructor", "No binding");
        }else{
            Log.d("Controllerconstructor", "Bound");
        }
    }


    public void onResume(){
        Timer time = new Timer();
        time.schedule(new TimerTask(),0, 20000);

    }


    public void setStartPosition(double startLong, double startLat) {
        this.currentLong = startLong;
        this.currentLat = startLat;
    }

    public void unRegisterFromGroup(String s) {
    }

    private class LocList implements android.location.LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            currentLat = location.getLatitude();
            currentLong = location.getLongitude();
            dataFragment.setCurrentLat(location.getLatitude());
            dataFragment.setCurrentLong(location.getLongitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    public void onDestroy(){
        if(bound){

            if(mainActivity.isFinishing()){
                mainActivity.getFragmentManager().beginTransaction().remove(dataFragment).commit();
                commService.stopService(new Intent(mainActivity, CommService.class));
            }
            mainActivity.unbindService(serviceConnection);
            listener.stopListener();
            bound = false;
        }

    }

    public void connect(){
        connected = true;
        dataFragment.setConnected(connected);
        commService.connect();

    }

    public void disconnectClicked(){
        if(connected){
            connected = false;
            dataFragment.setConnected(connected);
            commService.disconnect();
        }
    }

    public void setFragment(int fragment) {
        switch(fragment){
            case R.id.map:
                currentFragment = 0;
                break;
            case R.id.members:
                currentFragment = 1;
                break;
            case R.id.groups:
                currentFragment = 2;
                break;
            case R.id.register:
                currentFragment = 3;
                break;

        }
        dataFragment.setCurrentFragment(currentFragment);
        switchFragment(currentFragment);

    }

    public void switchFragment(int id){
        switch(id){
            case 0:
                mainActivity.setFragment(mapFragment, true);
                break;
            case 1:
                mainActivity.setFragment(memberFragment, true);
                break;
            case 2:
                mainActivity.setFragment(groupFragment, true);
                break;
            case 3:
                mainActivity.setFragment(registerFragment, true);
                break;
        }
    }

    public void setGroupAdapter() {
        JSONObject groupsObject = serverCommands.currentGroups();
        if(commService != null){
            commService.send(groupsObject);
        }
    }


    public void setMemberAdapter(){
        JSONObject membersObject = serverCommands.groupMembers(dataFragment.getCurrentGroup());
        Log.d("CONTROLLER", "CALLING" + commService);
        if(commService != null){
            commService.send(membersObject);
        }

        memberFragment.setData(gatherAllMembers());

    }

    public ArrayList<Member> gatherAllMembers(){

        ArrayList<String> myGroups = dataFragment.getMyGroups();
        ArrayList<Member> allMembers = new ArrayList<>();
        for(int i = 0; i < myGroups.size(); i++){
            Log.v("CONTROLLER FIRST GROUP", myGroups.get(i));
            ArrayList<Member> tempList = dataFragment.getFromMap(myGroups.get(i));

            for(int j = 0; j < tempList.size(); j++){
                allMembers.add(tempList.get(j));
            }
        }

        return allMembers;
    }

    public String register(String name, String group) {
        if(name.equals("") || group.equals("")){
            return "Empty field or missing field";
        }
        currentUsername = name;
        dataFragment.setCurrentUsername(name);
        dataFragment.setCurrentGroup(group);

        JSONObject registerObject = serverCommands.register(group, name);
        commService.send(registerObject);

        return "Registration successful!";
    }

    public String registerToExistingGroup(String group){
        if(currentUsername.equals("")){
            Toast.makeText(mainActivity, "You need to register before joining a group", Toast.LENGTH_SHORT).show();
            return "";
        }
        JSONObject registerObject = serverCommands.register(group, dataFragment.getCurrentUsername());
        commService.send(registerObject);
        return "";
    }


    public void rescueMission() {
        currentFragment = dataFragment.getCurrentFragment();
        currentUsername = dataFragment.getCurrentUsername();
        switchFragment(currentFragment);
    }


    public void setMarkers() {
        mapFragment.placeMarker(dataFragment.getCurrentPositionList());
    }

    public void setGroupFragment(String groupName) {
        JSONObject groupObject = serverCommands.groupMembers(groupName);
        commService.send(groupObject);


        mainActivity.setFragment(displayFrag, true);
    }

    public void setMemberInformation() {
        ArrayList<Member> tempList = dataFragment.getClickedGroupList();

        displayFrag.setBtnDeRegister(false);
        displayFrag.setBtnRegister(true);

        for(Member mem: tempList){
            if (mem.getName().equals(currentUsername)){
                displayFrag.setBtnDeRegister(true);
                displayFrag.setBtnRegister(false);
            }
            displayFrag.setTvGroupMembers(mem.getName());
        }

        displayFrag.setTvGroupName(dataFragment.getClickedGroup());
    }

    public int getCurrentFragment() {
        return currentFragment;
    }

    public void updateGroupFragment(GroupFragment groupFragment) {
        GroupAdapter g = new GroupAdapter(groupFragment);
        groupFragment.setAdapter(g);
        JSONObject group = serverCommands.currentGroups();

        if(commService != null) {
            commService.send(group);
        }else{
            groupFragment.updateGroups(dataFragment.getGroupList());
        }

    }

    private class ServiceConnection implements android.content.ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            CommService.LocalService ls = (CommService.LocalService) iBinder;
            commService = ls.getService();
            bound = true;
            listener = new Listener();
            listener.start();
            if(connected == false){
                connect();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound = false;
        }
    }

    private class Listener extends Thread{
        public void stopListener(){
            interrupt();
            listener = null;
        }

        @Override
        public void run() {
            String message;
            Exception exception;
            while(listener != null){
                try{
                    message = commService.recieve();
                    mainActivity.runOnUiThread(new UpdateUI(message));
                }catch(InterruptedException e){
                    e.printStackTrace();
                    listener = null;
                }

            }
        }
    }

    private class TimerTask extends java.util.TimerTask{

        @Override
        public void run() {
            setPosition();
        }
    }

    public void setPosition(){
        if(connected != false && !dataFragment.getCurrentUsername().equals("")){
            ArrayList<String> temp = dataFragment.getMyGroups();
            for(int i = 0; i < temp.size(); i++) {
                JSONObject sendObject = serverCommands.setPosition(dataFragment.getFromIdMap(temp.get(i)), String.valueOf(currentLat), String.valueOf(currentLong));
                commService.send(sendObject);
            }
        }

    }

    private class UpdateUI implements Runnable{
        private String message;

        public UpdateUI(String message){
            this.message = message;
        }

        @Override
        public void run() {
            recievedData(message);
            Exception exception = commService.getException();
            if("CONNECTED".equals(message)){
                connected = true;
            }else if("CLOSED".equals(message)){
                connected = false;
            }else if("EXCEPTION".equals(message) && exception != null){
                message = exception.toString();
            }
        }
    }

    public void recievedData(String message){
        JSONObject recievedObject;
        String type, id, group;
        JSONArray arr;
        ArrayList<Member> memberList;
        ArrayList<String> groupList;
        ArrayList<Member> positionList;
        try{
            recievedObject = new JSONObject(message);
            type = recievedObject.getString(ServerCommands._TYPE);


            switch(type){
                case ServerCommands._REGISTER:
                    group = recievedObject.getString(ServerCommands._GROUP);
                    id = recievedObject.getString(ServerCommands._ID);
                    Log.d("RECIEVED ID : ", id);
                    dataFragment.addToIdMap(group, id);
                    dataFragment.addGroup(group);
                    dataFragment.addToMap(group, new ArrayList<Member>());
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationListener);
                    break;

                case ServerCommands._MEMBERS:
                    arr = recievedObject.getJSONArray(ServerCommands._MEMBERS);
                    memberList = new ArrayList<>();
                    JSONObject member;
                    for(int i = 0; i < arr.length(); i++){
                        member = arr.getJSONObject(i);
                        memberList.add(new Member(member.getString(ServerCommands._MEMBER)));
                    }
                    dataFragment.setClickedGroup(recievedObject.getString(ServerCommands._GROUP));
                    dataFragment.setClickedGroupList(memberList);
                    break;

                case ServerCommands._GROUPS:
                    arr = recievedObject.getJSONArray(ServerCommands._GROUPS);
                    groupList = new ArrayList<>();
                    JSONObject group1;
                    for(int i = 0; i < arr.length(); i++){
                        group1 = arr.getJSONObject(i);
                        groupList.add(group1.getString(ServerCommands._GROUP));
                    }

                    dataFragment.setGroupList(groupList);
                    groupFragment.updateGroups(groupList);
                    break;

                case ServerCommands._UNREGISTER:

                    break;

                case ServerCommands._LOCATIONS:
                    arr = recievedObject.getJSONArray(ServerCommands._LOCATION);
                    group = recievedObject.getString(ServerCommands._GROUP);
                    positionList = new ArrayList<>();
                    JSONObject member1;
                    for(int i = 0; i < arr.length(); i++){
                        member1 = arr.getJSONObject(i);
                        positionList.add(new Member(member1.getString(ServerCommands._MEMBER), member1.getString(ServerCommands._LONGITUDE), member1.getString(ServerCommands._LATITUDE), group));
                    }

                    ArrayList<Member> tempList = dataFragment.getFromMap(group);

                    if(tempList.size() != 0) {
                        if (tempList.size() < positionList.size()) {
                            for (int i = 0; i < positionList.size(); i++) {
                                for (int j = 0; j < tempList.size(); j++) {
                                    if (positionList.get(j).getName().equals(tempList.get(j).getName())) {
                                        positionList.get(j).setShowOnMap(tempList.get(j).isShowOnMap());
                                    }
                                }
                            }
                        } else {
                            for (int i = 0; i < tempList.size(); i++) {
                                for (int j = 0; j < positionList.size(); j++) {
                                    if (positionList.get(j).getName().equals(tempList.get(j).getName())) {
                                        positionList.get(j).setShowOnMap(tempList.get(j).isShowOnMap());
                                    }
                                }
                            }

                        }
                    }

                    dataFragment.addToMap(group, positionList);

                    break;

                case ServerCommands._EXCEPTION:
                    JSONObject json = serverCommands.register(dataFragment.getCurrentUsername(), dataFragment.getCurrentGroup());
                    commService.send(json);

                    break;

            }

        }catch(JSONException e){

        }

    }


}
