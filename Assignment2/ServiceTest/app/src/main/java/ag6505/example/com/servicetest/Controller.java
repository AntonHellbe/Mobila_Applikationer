package ag6505.example.com.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import f8.Expression;

/**
 * Created by Anton on 2017-09-29.
 */

public class Controller {
    private MainActivity mainActivity;
    private CommService commService;
    private boolean connected = false;
    private ServiceConnection serviceConnection;
    private Listener listener;
    private boolean bound = false;
    private ServerCommands serverCommands;

    public Controller(MainActivity mainActivity, Bundle savedInstance){
        this.mainActivity = mainActivity;
        serverCommands = new ServerCommands();

        Intent intent = new Intent(mainActivity, CommService.class);
        if(savedInstance == null)
            mainActivity.startService(intent);
        else
            connected = savedInstance.getBoolean("CONNECTED", false);
        serviceConnection = new ServiceConnection();
        boolean result = mainActivity.bindService(intent, serviceConnection, 0);
        if(!result){
            Log.d("Controller-constructor", "No binding");
        }else{
            Log.d("Controllerconstructor", "Bound");
        }
//        connectClicked();
    }

    public void onResume(){
        if(connected){
            mainActivity.setBtnConnect(false);
            mainActivity.setBtnDisconnect(true);
            mainActivity.setBtnSend(true);
        }
    }

    public void onDestroy(){
        if(bound){

            if(mainActivity.isFinishing()){
                commService.stopService(new Intent(mainActivity, CommService.class));
            }
            mainActivity.unbindService(serviceConnection);
            listener.stopListener();
            bound = false;
        }
    }

    public Bundle onSaveInstanceState(Bundle outState){
        outState.putBoolean("CONNECTED", connected);
        return outState;
    }
    public void connectClicked(){
        connected = true;
        commService.connect();

    }

    public void disconnectClicked(){
        if(connected){
            connected = false;
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

    private class ServiceConnection implements android.content.ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            CommService.LocalService ls = (CommService.LocalService) iBinder;
            commService = ls.getService();
            bound = true;
            listener = new Listener();
            listener.start();
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
                mainActivity.setBtnConnect(false);
                mainActivity.setBtnDisconnect(true);
                connected = true;
            }else if("CLOSED".equals(message)){
                mainActivity.setBtnConnect(true);
                mainActivity.setBtnDisconnect(false);
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
                    mainActivity.setTvGroup(group);
                    mainActivity.setTvId(id);
                    break;

                case ServerCommands._MEMBERS:
                    arr = recievedObject.getJSONArray(ServerCommands._MEMBERS);
                    memberList = new ArrayList<>();
                    JSONObject member;
                    for(int i = 0; i < arr.length(); i++){
                        member = arr.getJSONObject(i);
                        memberList.add(new Member(member.getString(ServerCommands._MEMBER)));
                    }
                    for(Member mem: memberList){
                        mainActivity.setTvMembers(mem.getName());
                    }
                    break;

                case ServerCommands._GROUPS:
                    arr = recievedObject.getJSONArray(ServerCommands._GROUPS);
                    groupList = new ArrayList<>();
                    JSONObject group1;
                    for(int i = 0; i < arr.length(); i++){
                        group1 = arr.getJSONObject(i);
                        groupList.add(new Group(group1.getString(ServerCommands._GROUP)));
                    }
                    for(Group g: groupList){
                        mainActivity.setTvGroup(g.getName());
                    }
                    break;

                case ServerCommands._UNREGISTER:

                    break;

                case ServerCommands._LOCATION:
                    id = recievedObject.getString(ServerCommands._ID);
                    longitude = recievedObject.getString(ServerCommands._LONGITUDE);
                    latitude = recievedObject.getString(ServerCommands._LATITUDE);
                    mainActivity.setTvId(id);

                    break;

            }

        }catch(JSONException e){

        }

    }

}
