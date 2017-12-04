package edu.unc.takoda.monumenthunt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class Game extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{


    //region Final Variables
    private final int RequestFineLocation = 1;
    public final double EarthRadius = 6371000;
    private final double monumentReachedDistance = 10; //in meters
    //endregion
    /*Location update called every some time interval
    to update the Location of the closest monument and
    check proximity to closest monument to see if it has been
    reached.
    Magnetometer sensor is called as fast as possible to provide
    guiding arrow to closest monument.
     */

    //region Sensor and Service variables
    private LocationRequest accurateLocReq, fastLocReq;
    private GoogleApiClient locationProvider;
    private SensorManager sensorManager;
    private Sensor magnetometer;
    private Sensor accelerometer;
    //endregion

    private double Latitude, Longitude;
    private MonumentData mD;
    private List<Monument> monuments;
    private int closestMonument, prevClosestMonument;//position in monuments list of the closest monument and previous closest monument
    private GuidingArrow arrow;
    private ImageView monumentImage;
    private TextView monumentNumberView;
    private TextView monumentDistanceView;
    protected int monumentsFound = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RequestFineLocation);
        }

        arrow = new GuidingArrow(findViewById(R.id.arrow), findViewById(R.id.north));

        if(locationProvider == null)
            locationProvider = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(arrow, magnetometer, 10000); //move these two to onStart/onStop?
        sensorManager.registerListener(arrow, accelerometer, 10000);

        mD = new MonumentData();
        monuments = mD.monumentList();

        monumentImage = (ImageView)findViewById(R.id.monumentImage);
        findClosestMonument();
        monumentImage.setImageDrawable(getResources().getDrawable(monuments.get(closestMonument).getDrawableID()));
        monumentNumberView = (TextView)findViewById(R.id.monumentsFound);
        monumentDistanceView = (TextView)findViewById(R.id.distanceView);
    }


    public void backButton(View v){
        Intent backIntent = new Intent(this, StartMenu.class);
        startActivity(backIntent);
    }

    /*
    When GPS gives an update, update current latitude and longitude
     then update the closest monument with findClosestMonument.
     Check if the closest monument is close enough to be
     warranted as "found", then update the destination the
     arrow is pointing at.
     */
    @Override
    public void onLocationChanged(Location loc){
        Latitude = loc.getLatitude();
        Longitude = loc.getLongitude();
        findClosestMonument();
        detectClosestMonument();
        prevClosestMonument = closestMonument;
        Monument temp = monuments.get(closestMonument);
        monumentDistanceView.setText("Distance to monument: " + String.valueOf(distanceFromPosition(temp.getLatitude(), temp.getLongitude())));
        //for optimization so that the drawable doesn't have to be set when the image doesn't change
        if(prevClosestMonument != closestMonument)
            monumentImage.setImageDrawable(getResources().getDrawable(monuments.get(closestMonument).getDrawableID()));
        arrow.setDestination(temp.getLatitude(), temp.getLongitude());
        Log.v("Monument", temp.getName());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle){
        initializeAccurateLocationRequest();
        initializeFastLocationRequest();
        try{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                LocationServices.FusedLocationApi.requestLocationUpdates(locationProvider, accurateLocReq, this);
                LocationServices.FusedLocationApi.requestLocationUpdates(locationProvider, fastLocReq, arrow);
            }
        }catch(SecurityException e){
            e.printStackTrace();
        }
    }

    /*
    Returns distance in meters from current position's Latitude
    and Longitude to specified latitude and longitude
     */
    private double distanceFromPosition(double latitude, double longitude){
        double lat1 = Math.toRadians(Latitude);
        double lat2 = Math.toRadians(latitude);
        double deltaLat = Math.toRadians(latitude - Latitude);
        double deltaLong = Math.toRadians(longitude - Longitude);
        double a = Math.pow(Math.sin(deltaLat / 2.0), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaLong / 2.0), 2);
        return EarthRadius * 2 * Math.atan2(Math.pow(a, .5), Math.pow(1 - a, .5));
    }

    private void findClosestMonument(){
        int position = 0;
        double distance = 999999999;
        for(int i = 0; i < monuments.size(); i++){
            double temp = distanceFromPosition(monuments.get(i).getLatitude(), monuments.get(i).getLongitude());
            if(temp < distance){
                distance = temp;
                position = i;
            }
        }
        closestMonument = position;
    }

    /*
    If the distance from the person's position to the
    closest monument is less than the distance specified
    in the constant monumentReachedDistance, remove the
    closest monument from the monuments list and call
    findClosestMonument to put a new monument in the
    closestMonument variable, otherwise, do nothing
     */
    private void detectClosestMonument(){
        Monument temp = monuments.get(closestMonument);
        if(distanceFromPosition(temp.getLatitude(), temp.getLongitude()) < monumentReachedDistance){
            monuments.remove(closestMonument);
            findClosestMonument();
            monumentsFound++;
            monumentNumberView.setText("Monuments Found: "+monumentsFound);
        }
    }

    //Interval at which GPS location is updated for *this* listener, 5 seconds for now
    private void initializeAccurateLocationRequest(){
        accurateLocReq = new LocationRequest();
        accurateLocReq.setInterval(1000);
        accurateLocReq.setFastestInterval(1000);
        accurateLocReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    //interval at which GPS location is updated for the compass
    private void initializeFastLocationRequest(){
        fastLocReq = new LocationRequest();
        fastLocReq.setInterval(200);
        fastLocReq.setFastestInterval(100);
        fastLocReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }



    //region App status methods that toggle Location Services
    @Override
    protected void onResume(){
        super.onResume();
        try{
            if(accurateLocReq != null)
                LocationServices.FusedLocationApi.requestLocationUpdates(locationProvider, accurateLocReq, this);
            if(fastLocReq != null)
                LocationServices.FusedLocationApi.requestLocationUpdates(locationProvider, fastLocReq, arrow);
        }catch(SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(locationProvider, this);
        LocationServices.FusedLocationApi.removeLocationUpdates(locationProvider, arrow);
    }

    @Override
    protected void onStart(){
        super.onStart();
        locationProvider.connect();
    }

    @Override
    protected void onStop(){
        super.onStop();
        locationProvider.disconnect();
    }
    //endregion

    //region Unimplemented Override Methods
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){

    }

    @Override
    public void onConnectionSuspended(int i){

    }
    //endregion

}
