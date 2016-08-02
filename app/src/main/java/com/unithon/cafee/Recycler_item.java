package com.unithon.cafee;

/**
 * Created by Youngdo on 2016-01-19.
 */
public class Recycler_item {
    int max_user_count, join_user_count;
    String workplace_name, title, text , workgroup_type, created_at;
    double latitude, longtitude;

    Recycler_item(int max_user_count, int join_user_count, String title, String text,
                  String workplace_name, String workgroup_type, String created_at, double latitude, double longtitude){
        this.max_user_count = max_user_count;
        this.join_user_count = join_user_count;
        this.workplace_name = workplace_name;
        this.workgroup_type = workgroup_type;
        this.created_at = created_at;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.title=title;
        this.text=text;
    }

    public int getMax_user_count() {
        return max_user_count;
    }

    public int getJoin_user_count() {
        return join_user_count;
    }

    public String getWorkplace_name() {
        return workplace_name;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getWorkgroup_type() {
        return workgroup_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }


}
