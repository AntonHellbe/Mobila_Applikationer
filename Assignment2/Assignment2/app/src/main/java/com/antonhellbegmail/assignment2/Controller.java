package com.antonhellbegmail.assignment2;

import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-29.
 */

public class Controller {

    private MainActivity mainActivity;
    private GroupFragment groupFragment;
    private MemberFragment memberFragment;
    private RegisterFragment registerFragment;
    private MapFragment mapFragment;
    private DataFragment dataFragment;
    private CommService commService;


    private String currentUsername;
    private boolean connected = false;
    private ServiceConnection serviceConnection;
    private Listener listener;
    private boolean bound = false;
    private ServerCommands serverCommands;
    private int currentFragment = 0;

    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        initDataFragment();
        initComService();

        this.groupFragment = new GroupFragment();
        this.mapFragment = new MapFragment();
        this.memberFragment = new MemberFragment();
        this.registerFragment = new RegisterFragment();



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
            Log.d("CONTROLLER", "BINDING EXISTING SERVICE!!!");
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
        Log.v("CONTROLLER", "CONNECTING");
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

    public void sendClicked() {
//        JSONObject temp = serverCommands.register("login_group", "Anton");
//        JSONObject temp = serverCommands.setPosition("1506699938309", "15.0", "45.0");
        JSONObject temp = serverCommands.currentGroups();
        Log.d("RECIEVED JSONOBJECT", temp.toString());
        commService.send(temp);

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
        commService.send(groupsObject);
    }

    public void setMemberAdapter(){
        JSONObject membersObject = serverCommands.groupMembers("login_group");
        Log.d("CONTROLLER", "CALLING" + commService);
        if(commService != null){
            commService.send(membersObject);
        }
    }

    public String register(String s) {
        if(s.equals("")){
            return "Empty username field";
        }
        currentUsername = s;
        dataFragment.setCurrentUsername(s);

        JSONObject registerObject = serverCommands.register("login_group", s);
        commService.send(registerObject);
        return "Registration successfull!";
    }

    public String getCurrentUsername() {
        return currentUsername;
    }



    public void rescueMission() {
        currentFragment = dataFragment.getCurrentFragment();
        currentUsername = dataFragment.getCurrentUsername();
//        initComService();
        switchFragment(currentFragment);
    }

    private class ServiceConnection implements android.content.ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            CommService.LocalService ls = (CommService.LocalService) iBinder;
            Log.d("CONTROLLER", "COMMSERVICE CONNECTED!!");
            commService = ls.getService();
            bound = true;
            listener = new Listener();
            listener.start();
            connect();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound = false;
        }
    }

    public void getCurrentMemberList(){
        memberFragment.setData(dataFragment.getCurrentMemberList());

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
        String type, id, group, longitude, latitude;
        JSONArray arr;
        ArrayList<Member> memberList;
        ArrayList<Group> groupList;
        try{
            recievedObject = new JSONObject(message);
            type = recievedObject.getString(ServerCommands._TYPE);


            switch(type){
                case ServerCommands._REGISTER:
                    group = recievedObject.getString(ServerCommands._GROUP);
                    id = recievedObject.getString(ServerCommands._ID);
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
                    break;

                case ServerCommands._GROUPS:
                    arr = recievedObject.getJSONArray(ServerCommands._GROUPS);
                    groupList = new ArrayList<>();
                    JSONObject group1;
                    for(int i = 0; i < arr.length(); i++){
                        group1 = arr.getJSONObject(i);
                        groupList.add(new Group(group1.getString(ServerCommands._GROUP)));
                    }

                    groupFragment.setAdapter(groupList);
                    break;

                case ServerCommands._UNREGISTER:

                    break;

                case ServerCommands._LOCATION:
                    id = recievedObject.getString(ServerCommands._ID);
                    longitude = recievedObject.getString(ServerCommands._LONGITUDE);
                    latitude = recievedObject.getString(ServerCommands._LATITUDE);

                    break;

            }

        }catch(JSONException e){

        }

    }

}
