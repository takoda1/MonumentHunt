package edu.unc.takoda.monumenthunt;

import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.util.Log;
import android.view.View;

/**
 * Created by takoda on 11/22/2017.
 */
/*
A class that takes a view in its constructor,
then controls that view to rotate/point in the
direction of the latitude/longitude
specified with the setDestination method by acting
as a location and sensor listener to obtain
the needed data.
 */
/*
Second view in constructor specific to this project only,
can easily be removed for reuse of class
 */
/*
Heading, bearing, and declination are needed to point arrow in the
direction of the specified latitude and longitude.
Heading is calculated every time onSensorChanged is called,
the arrow direction is changed every time onLocationChange is called,
where bearing and declination are then calculated
 */

public class GuidingArrow implements
        com.google.android.gms.location.LocationListener,
        SensorEventListener{

    private View arrow;
    private View north;

    private float[] gravityMatrix = new float[3];
    private float[] magneticMatrix = new float[3];

    private double heading;
    private double Latitude;
    private double Longitude;

    private float declination;

    public GuidingArrow(View arrow, View north){
        this.arrow = arrow; this.north = north;
    }

    @Override
    public void onLocationChanged(Location loc){
        double lat1 = Math.toRadians(loc.getLatitude()); double long1 = Math.toRadians(loc.getLongitude());
        double lat2 = Math.toRadians(Latitude); double long2 = Math.toRadians(Longitude);
        double y = Math.cos(lat2) * Math.sin(long2 - long1);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(long2 - long1);
        double bearing = Math.toDegrees(Math.atan2(y, x));
        bearing = (bearing + 360) % 360;

        GeomagneticField geoField = new GeomagneticField((float)loc.getLatitude(), (float)loc.getLongitude(), (float)loc.getAltitude(), System.currentTimeMillis());
        declination = geoField.getDeclination();

        /*Log.v("Location", ""+ loc.getLatitude()+ " "+loc.getLongitude());
        Log.v("bearing", ""+bearing);
        Log.v("HEADING", ""+heading);*/
        double temp = (heading - bearing) * -1;
        //Log.v("COMBINED", ""+temp);
        arrow.setRotation((float)temp);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        synchronized (this){
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                gravityMatrix[0] = event.values[0];
                gravityMatrix[1] = event.values[1];
                gravityMatrix[2] = event.values[2];
            }
            if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                magneticMatrix[0] = event.values[0];
                magneticMatrix[1] = event.values[1];
                magneticMatrix[2] = event.values[2];
            }
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, gravityMatrix, magneticMatrix);
                if(success){
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                heading = (float) Math.toDegrees(orientation[0]);
            }
            heading += declination;
            heading = (heading + 360) % 360;
        }
        north.setRotation((float)(heading * -1));
    }

    public void setDestination(double latitude, double longitude){
        Latitude = latitude;
        Longitude = longitude;
    }

    @Override
    public void onAccuracyChanged(Sensor s, int i){

    }
}
