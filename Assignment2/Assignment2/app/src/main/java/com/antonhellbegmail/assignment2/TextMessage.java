package com.antonhellbegmail.assignment2;

/**
 * Created by Anton on 2017-10-04.
 */

public class TextMessage {

    private String group;
    private String text;
    private String member;
    private String port;
    private String imageId;
    private String longitude;
    private String latitude;

    public TextMessage(String group, String member, String text){
        this.group = group;
        this.member = member;
        this.text = text;
        this.port = "";
        this.imageId = "";
        this.longitude = "";
        this.latitude = "";
    }

    public TextMessage(String group, String member, String text, String port, String imageId, String longitude, String latitude){
        this.group = group;
        this.member = member;
        this.text = text;
        this.port = port;
        this.imageId = imageId;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String id) {
        this.group = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
