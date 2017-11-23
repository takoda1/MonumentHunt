package edu.unc.takoda.monumenthunt;

import android.location.Location;

/**
 * Created by takoda on 11/18/2017.
 */

public class Monument{

    private double latitude, longitude;
    private int drawableID;
    private String name;

    public Monument(String name, double latitude, double longitude, int drawableID){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.drawableID = drawableID;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public int getDrawableID(){
        return drawableID;
    }

    public String getName(){
        return name;
    }
}
