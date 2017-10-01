package com.antonhellbegmail.assignment2;

/**
 * Created by Anton on 2017-09-29.
 */

public class Member {
    private String name;
    private String longitude;
    private String latitude;
    private String id;

    public Member(String name, String longitude, String latitude){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
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
}
