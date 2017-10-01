package com.antonhellbegmail.assignment2;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anton on 2017-09-29.
 */

public class ServerCommands {

    public static final String _REGISTER = "register";
    public static final String _UNREGISTER = "unregister";
    public static final String _MEMBERS = "members";
    public static final String _MEMBER = "member";
    public static final String _GROUPS = "groups";
    public static final String _GROUP = "group";
    public static final String _LOCATION = "location";
    public static final String _LOCATIONS = "locations";
    public static final String _NAME = "name";
    public static final String _TYPE = "type";
    public static final String _ID = "id";
    public static final String _LONGITUDE = "longitude";
    public static final String _LATITUDE = "latitude";
    public static final String _EXCEPTION = "exception" ;

    public ServerCommands(){

    }

    public JSONObject register(String group, String name){
        JSONObject newObject = new JSONObject();
        try{
            newObject.put(_TYPE, _REGISTER);
            newObject.put(_GROUP, group);
            newObject.put(_MEMBER, name);
        }catch (JSONException e){
            Log.d("SERVERCOMMANDS", "SOMETHING WENT WRONG");
        }

        return newObject;
    }

    public JSONObject deRegistration(String id){
        JSONObject newObject = new JSONObject();
        try{
            newObject.put(_TYPE, _UNREGISTER);
            newObject.put(_ID, id);
        }catch (JSONException e){

        }

        return newObject;
    }

    public JSONObject groupMembers(String name){
        JSONObject newObject = new JSONObject();
        try{
            newObject.put(_TYPE, _MEMBERS);
            newObject.put(_GROUP, name);
        }catch(JSONException e){

        }
        return newObject;
    }

    public JSONObject currentGroups(){
        JSONObject newObject = new JSONObject();
        try{
            newObject.put(_TYPE, _GROUPS);
        }catch(JSONException e){

        }

        return newObject;
    }

    public JSONObject setPosition(String id, String longitude, String latitude){
        JSONObject newObject = new JSONObject();
        try{
            newObject.put(_TYPE, _LOCATION);
            newObject.put(_ID, id);
            newObject.put(_LONGITUDE, longitude);
            newObject.put(_LATITUDE, latitude);

        }catch(JSONException e){

        }

        return newObject;
    }

}
