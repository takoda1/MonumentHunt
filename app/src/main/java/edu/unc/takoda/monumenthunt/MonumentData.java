package edu.unc.takoda.monumenthunt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takoda on 11/18/2017.
 */

public class MonumentData {
    /*private String[] names = {};
    private double[] latitudes = {};
    private double[] longitudes = {};
    private int[] drawableIDs = {};*/

    private static List<Monument> monuments = new ArrayList<Monument>();

    public MonumentData(){
        monuments.add(new Monument("Old Well", 35.91205448, -79.05123889, R.drawable.oldwell));
        /*for(int i = 0; i < names.length; i++){
            monuments.add(new Monument(names[i], latitudes[i], longitutdes[i], drawableIDs[i]));
        }*/
    }

    public ArrayList<Monument> monumentList(){
        ArrayList<Monument> ret = new ArrayList<Monument>();
        for(Monument m : monuments){
            ret.add(m);
        }
        return ret;
    }
}
