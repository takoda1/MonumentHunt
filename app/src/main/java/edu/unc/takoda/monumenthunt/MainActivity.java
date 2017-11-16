package edu.unc.takoda.monumenthunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public final double EarthRadius = 6371000;
    private double Latitude, Longitude;
    private List<Location> monuments = new ArrayLIst<Location>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
