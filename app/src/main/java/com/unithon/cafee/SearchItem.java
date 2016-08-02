package com.unithon.cafee;

/**
 * Created by Youngdo on 2016-07-31.
 */
public class SearchItem {
    String title, location;
    String latitude, longtitude;

    public String getLatitude() {
        return latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }
    SearchItem(String title, String location, String longtitude, String latitude){
        this.title = title;
        this.location = location;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }
}
