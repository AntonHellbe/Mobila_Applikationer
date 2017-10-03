package com.antonhellbegmail.assignment2;

import static com.antonhellbegmail.assignment2.R.string.group;

/**
 * Created by Anton on 2017-09-29.
 */

public class Member {
    private String name;

    @Override
    public String toString() {
        return this.name + " " + " " + this.latitude + " " + this.longitude + this.group + " " + this.showOnMap;
    }

    private String longitude;
    private String latitude;
    private String id;
    private boolean showOnMap;
    private String group;

    public Member(String name, String longitude, String latitude, String group){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.group = group;
        this.showOnMap = true;
    }

    public Member(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isShowOnMap() {
        return showOnMap;
    }

    public void setShowOnMap(boolean showOnMap) {
        this.showOnMap = showOnMap;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
