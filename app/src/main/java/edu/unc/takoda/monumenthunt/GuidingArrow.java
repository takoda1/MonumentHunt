package edu.unc.takoda.monumenthunt;

import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.view.View;

/**
 * Created by takoda on 11/22/2017.
 */
/*
A class that takes a view as a constructor,
then controls that view to rotate/point in the
direction of the latitude/longitude
specified with the setDestination method by acting
as a location and sensor listener to obtain
the needed data.
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

    private float[] gravityMatrix = new float[3];
    private float[] magneticMatrix = new float[3];

    private double heading;
    private double Latitude;
    private double Longitude;

    public GuidingArrow(View v){
        arrow = v;
    }

    @Override
    public void onLocationChanged(Location loc){
        double la = loc.getLatitude(); double lo = loc.getLongitude();
        double x = Math.cos(Latitude) * Math.sin(Longitude - lo);
        double y = Math.cos(la) * Math.sin(Latitude) - Math.sin(la) * Math.cos(Latitude) * Math.cos(Longitude - lo);
        double bearing = Math.atan2(x, y);
        GeomagneticField geoField = new GeomagneticField((float)la, (float)lo, (float)loc.getAltitude(), System.currentTimeMillis());
        heading += geoField.getDeclination();
        heading = (bearing - heading) * -1;
        arrow.setRotation((float)heading);
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
        }
    }

    public void setDestination(double latitude, double longitude){
        Latitude = latitude;
        Longitude = longitude;
    }

    @Override
    public void onAccuracyChanged(Sensor s, int i){

    }
}
