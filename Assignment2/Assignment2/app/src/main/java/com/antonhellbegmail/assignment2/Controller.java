package com.antonhellbegmail.assignment2;

import android.*;
import android.Manifest;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;


public class Controller {

    private android.location.LocationListener locationListener;
    private MainActivity mainActivity;
    private GroupFragment groupFragment;
    private MemberFragment memberFragment;
    private RegisterFragment registerFragment;
    private MapFragment mapFragment;
    private DisplayGroupFragment displayFrag;
    private ChatFragment chatFragment;
    private DataFragment dataFragment;
    private CommService commService;

    private Bitmap bitMapTest;


    private String currentUsername = "";
    private boolean connected = false;
    private ServiceConnection serviceConnection;
    private Listener listener;
    private boolean bound = false;
    private ServerCommands serverCommands;
    private Timer timer;
    private int currentFragment = 0;

    private double currentLat = 0;
    private double currentLong = 0;

    private LocationManager locationManager;

    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        initDataFragment();
        initComService();

        this.groupFragment = new GroupFragment();
        this.mapFragment = new MapFragment();
        this.memberFragment = new MemberFragment();
        this.registerFragment = new RegisterFragment();
        this.displayFrag = new DisplayGroupFragment();
        this.chatFragment = new ChatFragment();


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
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask(), 0, 30000);
        }
    }

    public void locationChanged(Location location){
        if(location != null) {
            this.currentLong = location.getLongitude();
            this.currentLat = location.getLatitude();
            dataFragment.setCurrentLat(currentLat);
            dataFragment.setCurrentLong(currentLong);
            Log.d("CONTROLLER", "GOT POSITION" + location.getLatitude() + " " + location.getLongitude());
        }
    }


    public void setStartPosition(double startLong, double startLat) {
        this.currentLong = startLong;
        this.currentLat = startLat;
        dataFragment.setCurrentLat(startLat);
        dataFragment.setCurrentLong(startLong);
    }

    public void unRegisterFromGroup(String s) {
        String id = dataFragment.getFromIdMap(s);
        if(id == null){
            return;
        }
        JSONObject deRegistration = serverCommands.deRegistration(id);
        dataFragment.removeFromIdMap(s);
        dataFragment.removeGroup(s);
        commService.send(deRegistration);
        displayFrag.setBtnDeRegister(false);
        displayFrag.setBtnRegister(true);
        JSONObject updatedGroup = serverCommands.groupMembers(dataFragment.getClickedGroup());
        commService.send(updatedGroup);
        Toast.makeText(displayFrag.getActivity(), "Deregistration succesfull", Toast.LENGTH_SHORT).show();
    }

    public void setShowOnMap(String groupname, String showOnMap, boolean b) {
        ArrayList<Member> userList = dataFragment.getFromMap(groupname);
        for(int i = 0; i < userList.size(); i++){
            if(userList.get(i).getName().equals(showOnMap)){
                userList.get(i).setShowOnMap(b);
                Log.d("SHOWONMAP CHNAGED FOR", userList.get(i).toString() + b);
            }
        }
    }

    public void sendMessage(String text) {
        if(dataFragment.getMyGroups().size() == 0){
            return;
        }
        String id = dataFragment.getFromIdMap(dataFragment.getActiveChatGroup());
        JSONObject temp = serverCommands.textMessage(text, id);
        commService.send(temp);
        chatFragment.clearMessageField();

    }

    public void postMessages() {
        ArrayList<String> myGroups = dataFragment.getMyGroups();
        chatFragment.setSpinnerAdapter(myGroups);
        ArrayList<TextMessage> filteredMessages = new ArrayList<>();
        ArrayList<TextMessage> messageList = dataFragment.getMessageList();
        for(TextMessage m: messageList){
            if(m.getGroup().equals(dataFragment.getActiveChatGroup())){
                filteredMessages.add(m);
            }
        }
        chatFragment.setData(filteredMessages);
    }

    public void setRegisterState() {
        registerFragment.setBtnRegister(dataFragment.isNotRegistered());
    }

    public void compress(String pathToPicture) {
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();

        bmpOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathToPicture, bmpOptions);

        int photoW = bmpOptions.outWidth;
        int photoH = bmpOptions.outHeight;

        int scaleFactor = Math.min(photoW / 50, photoH / 50);

        bmpOptions.inJustDecodeBounds = false;
        bmpOptions.inSampleSize = scaleFactor;
        Bitmap bitmap = BitmapFactory.decodeFile(pathToPicture, bmpOptions);

        bitMapTest = bitmap;

        JSONObject temp = serverCommands.sendImage(dataFragment.getFromIdMap(dataFragment.getActiveChatGroup()),chatFragment.getEtMessage(),
                String.valueOf(dataFragment.getCurrentLong()), String.valueOf(dataFragment.getCurrentLat()));
        commService.send(temp);

        chatFragment.clearMessageField();


    }

    private byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void updateChatGroup(String group) {
        dataFragment.setActiveChatGroup(group);
        ArrayList<TextMessage> filteredMessages = new ArrayList<>();
        ArrayList<TextMessage> messageList = dataFragment.getMessageList();
        for(TextMessage m: messageList){
            if(m.getGroup().equals(dataFragment.getActiveChatGroup())){
                filteredMessages.add(m);
            }
        }
        chatFragment.setData(filteredMessages);


    }

    public void startCamera() {
        mainActivity.startCameraActivity(2);
    }

    public void onPause() {
        if(mainActivity.isFinishing()){
            mainActivity.getFragmentManager().beginTransaction().remove(dataFragment).commit();
        }
    }

    public void addNewGroup(String groupName) {
        if(currentUsername.equals("") || groupName.equals("")){
            Toast.makeText(groupFragment.getActivity(), "Error occured when trying to add group", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject registerObject = serverCommands.register(groupName, dataFragment.getCurrentUsername());
        commService.send(registerObject);

        JSONObject refreshObject = serverCommands.currentGroups();
        commService.send(refreshObject);
    }

    public void refreshGroups() {
        JSONObject refreshObject = serverCommands.currentGroups();
        commService.send(refreshObject);
    }


    public void onDestroy(){

        if(bound){
            if(mainActivity.isFinishing()){
                commService.disconnect();
                commService.stopService(new Intent(mainActivity, CommService.class));
            }
            mainActivity.unbindService(serviceConnection);
            listener.stopListener();
            bound = false;
            timer.cancel();
            timer.purge();
            timer = null;
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
            case R.id.chat:
                currentFragment = 4;
                break;

        }
        dataFragment.setCurrentFragment(currentFragment);
        switchFragment(currentFragment);

    }

    public void switchFragment(int id){
        switch(id){
            case 0:
                mainActivity.setFragment(mapFragment, true);
                dataFragment.setChatActive(false);
                break;
            case 1:
                mainActivity.setFragment(memberFragment, true);
                dataFragment.setChatActive(false);
                break;
            case 2:
                mainActivity.setFragment(groupFragment, true);
                dataFragment.setChatActive(false);
                break;
            case 3:
                mainActivity.setFragment(registerFragment, true);
                dataFragment.setChatActive(false);
                break;
            case 4:
                mainActivity.setFragment(chatFragment, true);
                dataFragment.setChatActive(true);
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
        memberFragment.setData(gatherAllMembers());

    }

    public ArrayList<Member> gatherAllMembers(){

        ArrayList<String> myGroups = dataFragment.getMyGroups();
        HashSet<Member> hashSet = new HashSet<>();
        ArrayList<Member> allMembers = new ArrayList<>();
        for(int i = 0; i < myGroups.size(); i++){
            ArrayList<Member> tempList = dataFragment.getFromMap(myGroups.get(i));

            for(int j = 0; j < tempList.size(); j++){
                hashSet.add(tempList.get(j));
            }
        }
        for(Member m: hashSet){
            allMembers.add(m);
        }

        return allMembers;
    }

    public String register(String name, String group) {
        if(name.equals("") || group.equals("")){
            return "Empty field or missing field";
        }

        if(dataFragment.getMyGroups().size() != 0){
            return "You are already registered!";
        }
        currentUsername = name;
        dataFragment.setCurrentUsername(name);
        dataFragment.setNotRegistered(false);
        dataFragment.setActiveChatGroup(group);
        mainActivity.setUsername(currentUsername);
        registerFragment.setBtnRegister(false);
        registerFragment.clearGroupName();
        registerFragment.clearTvUsername();


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

        JSONObject updatedGroup = serverCommands.groupMembers(dataFragment.getClickedGroup());
        commService.send(updatedGroup);
        displayFrag.setBtnRegister(false);
        displayFrag.setBtnDeRegister(true);
        Toast.makeText(displayFrag.getActivity(), "Registration Successfull!", Toast.LENGTH_SHORT).show();
        return "";
    }


    public void rescueMission() {
        currentFragment = dataFragment.getCurrentFragment();
        currentUsername = dataFragment.getCurrentUsername();
        mainActivity.setUsername(currentUsername);
        switchFragment(currentFragment);
    }


    public void setMarkers() {
        mapFragment.placeMarker(gatherAllMembers(), dataFragment.getMessageList());
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

    public DataFragment getDataFragment(){
        return this.dataFragment;
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
        if(connected != false && !dataFragment.getCurrentUsername().equals("") && commService != null){
            ArrayList<String> temp = dataFragment.getMyGroups();
            for(int i = 0; i < temp.size(); i++) {
                JSONObject sendObject = serverCommands.setPosition(dataFragment.getFromIdMap(temp.get(i)),
                        String.valueOf(dataFragment.getCurrentLong()), String.valueOf(dataFragment.getCurrentLat()));
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
        String type, id, group, text, mem, imageId, port, longi, lati;
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
                    dataFragment.addToIdMap(group, id);
                    dataFragment.addGroup(group);
                    dataFragment.addToMap(group, new ArrayList<Member>());
                    break;

                case ServerCommands._MEMBERS:
                    arr = recievedObject.getJSONArray(ServerCommands._MEMBERS);
                    group = recievedObject.getString(ServerCommands._GROUP);
                    memberList = new ArrayList<>();
                    JSONObject member;
                    for(int i = 0; i < arr.length(); i++){
                        member = arr.getJSONObject(i);
                        memberList.add(new Member(member.getString(ServerCommands._MEMBER)));
                    }
                    ArrayList<String> myGroups = dataFragment.getMyGroups();

                    displayFrag.setBtnDeRegister(false);
                    displayFrag.setBtnRegister(true);
                    for(String s: myGroups){
                        if(s.equals(group)){
                            displayFrag.setBtnRegister(false);
                            displayFrag.setBtnDeRegister(true);
                        }
                    }
                    dataFragment.setClickedGroup(recievedObject.getString(ServerCommands._GROUP));
                    displayFrag.setData(memberList);
                    displayFrag.setTvGroupName(dataFragment.getClickedGroup());
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

                    if(tempList.size() != 0){
                        for(Member m: tempList){
                            for(Member m1: positionList){
                                if(m1.getName().equals(m.getName())){
                                    m1.setShowOnMap(m.isShowOnMap());
                                }
                            }
                        }
                    }
                    dataFragment.addToMap(group, positionList);

                    break;

                case ServerCommands._TEXTCHAT:
                    group = recievedObject.getString(serverCommands._GROUP);
                    text = recievedObject.getString(serverCommands._TEXT);
                    mem = recievedObject.getString(serverCommands._MEMBER);
                    TextMessage temp = new TextMessage(group,mem, text);
                    dataFragment.addTextMessage(temp);
                    if(dataFragment.getChatActive() && group.equals(dataFragment.getActiveChatGroup())) {
                        ArrayList<TextMessage> newMessages = new ArrayList<>();
                        ArrayList<TextMessage> list = dataFragment.getMessageList();
                        for(TextMessage m: list){
                            if(m.getGroup().equals(group)){
                                newMessages.add(m);
                            }
                        }
                        chatFragment.setData(newMessages);
                    }
                    break;

                case ServerCommands._UPLOAD:
                    imageId = recievedObject.getString(ServerCommands._IMAGEID);
                    port = recievedObject.getString(ServerCommands._PORT);
                    dataFragment.addToImageMap(imageId, bitmapToByte(bitMapTest));
                    new SendImageTask(bitMapTest).execute(imageId, port, ServerCommands._IP);

                    break;

                case ServerCommands._IMAGE_CHAT:
                    group = recievedObject.getString(ServerCommands._GROUP);
                    mem = recievedObject.getString(ServerCommands._MEMBER);
                    text = recievedObject.getString(ServerCommands._TEXT);
                    longi = recievedObject.getString(ServerCommands._LONGITUDE);
                    lati = recievedObject.getString(ServerCommands._LATITUDE);
                    imageId = recievedObject.getString(ServerCommands._IMAGEID);
                    port = recievedObject.getString(ServerCommands._PORT);
                    TextMessage imMessage = new TextMessage(group, mem, text, port, imageId, longi, lati);
                    dataFragment.addTextMessage(imMessage);
                    if(dataFragment.getChatActive() && group.equals(dataFragment.getActiveChatGroup())) {
                        ArrayList<TextMessage> newMessages = new ArrayList<>();
                        ArrayList<TextMessage> list = dataFragment.getMessageList();
                        for(TextMessage m: list){
                            if(m.getGroup().equals(group)){
                                newMessages.add(m);
                            }
                        }
                        chatFragment.setData(newMessages);
                    }

                    break;



                case ServerCommands._EXCEPTION:
                    JSONObject json = serverCommands.register(dataFragment.getCurrentUsername(), dataFragment.getCurrentGroup());
                    commService.send(json);

                    break;

            }

        }catch(JSONException e){

        }

    }

    public String getCurrentUsername(){
        return dataFragment.getCurrentUsername();
    }


}
