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
    public static final String _EXCEPTION = "exception";
    public static final String _TEXTCHAT = "textchat";
    public static final String _TEXT = "text";
    public static final String _IMAGE_CHAT = "imagechat";
    public static final String _UPLOAD = "upload";
    public static final String _IMAGEID = "imageid";
    public static final String _PORT = "port";
    public static final String _IP = "195.178.227.53";

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

    public JSONObject textMessage(String message, String id){
        JSONObject textObject = new JSONObject();
        try{
            textObject.put(_TYPE, _TEXTCHAT);
            textObject.put(_ID, id);
            textObject.put(_TEXT, message);
        }catch(JSONException e){

        }

        return textObject;
    }

    public JSONObject sendImage(String id, String text, String longitude, String latitude){
        JSONObject temp = new JSONObject();
        try{
            temp.put(_TYPE, _IMAGE_CHAT);
            temp.put(_ID, id);
            temp.put(_TEXT, text);
            temp.put(_LONGITUDE, longitude);
            temp.put(_LATITUDE, latitude);
        }catch (JSONException e){

        }

        return temp;
    }

}
