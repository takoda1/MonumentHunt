package edu.unc.takoda.monumenthunt;

import android.Manifest;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{


    private final int RequestFineLocation = 1;
    public final double EarthRadius = 6371000;


    /*Location update called every some time interval
    to update the Location of the closest monument and
    check proximity to closest monument to see if it has been
    reached.
    Magnetometer sensor is called as fast as possible to provide
    guiding arrow to closest monument.
     */
    private LocationRequest accurateLocReq;
    private GoogleApiClient locationProvider;
    private SensorManager sensorManager;
    private Sensor magnetometer;

    private double Latitude, Longitude;

    private List<Location> monuments = new ArrayList<Location>();
    private Location closestMonument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RequestFineLocation);
        }

        initializeAccurateLocationRequest();
        if(locationProvider == null)
            locationProvider = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        
    }

    @Override
    protected void onResume(){
        super.onResume();
        try{
            if(accurateLocReq != null)
                LocationServices.FusedLocationApi.requestLocationUpdates(locationProvider, accurateLocReq, this);
        }catch(SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(locationProvider, this);
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

    @Override
    public void onLocationChanged(Location loc){

    }

    @Override
    public void onConnected(@Nullable Bundle bundle){

    }


    private double distanceFromPosition(double latitude, double longitude){
        double a = Math.pow(Math.sin((Latitude - latitude) / 2.0), 2) + Math.cos(Latitude) * Math.cos(latitude) * Math.pow(Math.sin((Longitude - longitude) / 2.0), 2);
        return EarthRadius * 2 * Math.atan2(Math.pow(a, .5), Math.pow(1 - a, .5));
    }

    private void closestLocations(){
        for(int i = 0; i < 2 && i < monuments.size(); i++){
            for(int j = i; j < monuments.size(); j++){

            }
        }
    }

    private void initializeAccurateLocationRequest(){
        accurateLocReq = new LocationRequest();
        accurateLocReq.setInterval(2000);
        accurateLocReq.setFastestInterval(1000);
        accurateLocReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){

    }

    @Override
    public void onConnectionSuspended(int i){

    }
}
