package com.antonhellbegmail.assignment2;

import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Controller {

    private MainActivity mainActivity;
    private GroupFragment groupFragment;
    private MemberFragment memberFragment;
    private RegisterFragment registerFragment;
    private MapFragment mapFragment;
    private DisplayGroupFragment displayFrag;
    private DataFragment dataFragment;
    private CommService commService;


    private String currentUsername;
    private boolean connected = false;
    private ServiceConnection serviceConnection;
    private Listener listener;
    private boolean bound = false;
    private ServerCommands serverCommands;
    private int currentFragment = 0;

    private FusedLocationProviderClient mFusedLocationClient;

    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        initDataFragment();
        initComService();

        this.groupFragment = new GroupFragment();
        this.mapFragment = new MapFragment();
        this.memberFragment = new MemberFragment();
        this.registerFragment = new RegisterFragment();
        this.displayFrag = new DisplayGroupFragment();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mainActivity);
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
        if(connected){

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
        }else {
            memberFragment.setData(dataFragment.getCurrentMemberList());
        }




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
        JSONObject registerObject = serverCommands.register(group, currentUsername);
        commService.send(registerObject);
        return "";
    }

    public String getCurrentUsername() {
        return currentUsername;
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
        ArrayList<Member> tempList = dataFragment.getCurrentMemberList();
        for(Member mem: tempList){
            displayFrag.setTvGroupMembers(mem.getName());
        }
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
            groupFragment.updateGroups(dataFragment.getCurrentGroupList());
        }

    }

    private class PositionUpdater implements Runnable{

        Handler handler = new Handler();

        @Override
        public void run() {
            try {
                initPosition();
            }catch(Exception e){

            }finally {
                handler.postDelayed(this, 30000);
            }

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
        ArrayList<Group> groupList;
        ArrayList<Member> positionList;
        try{
            recievedObject = new JSONObject(message);
            type = recievedObject.getString(ServerCommands._TYPE);


            switch(type){
                case ServerCommands._REGISTER:
                    group = recievedObject.getString(ServerCommands._GROUP);
                    id = recievedObject.getString(ServerCommands._ID);
                    dataFragment.setCurrentId(id);
                    Log.d("RECIEVED ID : ", id);
                    mainActivity.runOnUiThread(new PositionUpdater());
                    break;

                case ServerCommands._MEMBERS:
                    arr = recievedObject.getJSONArray(ServerCommands._MEMBERS);
                    memberList = new ArrayList<>();
                    JSONObject member;
                    for(int i = 0; i < arr.length(); i++){
                        member = arr.getJSONObject(i);
                        memberList.add(new Member(member.getString(ServerCommands._MEMBER)));
                    }
                    dataFragment.setCurrentMemberList(memberList);
                    memberFragment.setData(memberList);

                    break;

                case ServerCommands._GROUPS:
                    arr = recievedObject.getJSONArray(ServerCommands._GROUPS);
                    groupList = new ArrayList<>();
                    JSONObject group1;
                    for(int i = 0; i < arr.length(); i++){
                        group1 = arr.getJSONObject(i);
                        groupList.add(new Group(group1.getString(ServerCommands._GROUP)));
                    }

                    dataFragment.setCurrentGroupList(groupList);
                    groupFragment.updateGroups(groupList);
                    break;

                case ServerCommands._UNREGISTER:

                    break;

                case ServerCommands._LOCATIONS:
                    arr = recievedObject.getJSONArray(ServerCommands._LOCATION);
                    positionList = new ArrayList<>();
                    JSONObject member1;
                    for(int i = 0; i < arr.length(); i++){
                        member1 = arr.getJSONObject(i);
                        positionList.add(new Member(member1.getString(ServerCommands._MEMBER), member1.getString(ServerCommands._LONGITUDE), member1.getString(ServerCommands._LATITUDE)));
                    }

                    dataFragment.setCurrentPositionList(positionList);
                    break;

                case ServerCommands._EXCEPTION:
                    JSONObject json = serverCommands.register(dataFragment.getCurrentUsername(), dataFragment.getCurrentGroup());
                    commService.send(json);

                    break;

            }

        }catch(JSONException e){

        }

    }

    public void initPosition(){
        mFusedLocationClient.getLastLocation().
                addOnSuccessListener(mainActivity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            JSONObject tempObj = serverCommands.setPosition(dataFragment.getCurrentId(), String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));
                            commService.send(tempObj);
                        }
                    }
                });
    }

}
