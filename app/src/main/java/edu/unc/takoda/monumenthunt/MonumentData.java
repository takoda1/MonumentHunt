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
        monuments.add(new Monument("Old East", 35.9123, -79.0508, R.drawable.oldeast));
        monuments.add(new Monument("Davie Poplar", 35.9129, -79.0517, R.drawable.daviepoplar));
        monuments.add(new Monument("Carolina Inn", 35.9099, -79.0546, R.drawable.carolinainn));
        monuments.add(new Monument("Charlie Choo Choo Justice", 35.907337, -79.049395, R.drawable.choochoo));
        monuments.add(new Monument("9/11 Memorial", 35.907530, -79.046390, R.drawable.nineeleven));
        monuments.add(new Monument("Bell Tower", 35.9086, -79.0492, R.drawable.belltower));
        monuments.add(new Monument("Thomas Wolfe Memorial", 35.910650,-79.049410,  R.drawable.wolfe));
        monuments.add(new Monument("Eve Carson Memorial Gardens", 35.910750, -79.0505480, R.drawable.evecar));
        monuments.add(new Monument("Carolina Alumni in Memory of those Lost in Military Service",
                35.911070, -79.052380, R.drawable.vetmemorial));
        monuments.add(new Monument("Playmakers Theatre", 35.9120, -79.0503, R.drawable.playmakers));
        monuments.add(new Monument("Caldwell Monument", 35.913290, -79.052200, R.drawable.oblisk));
        monuments.add(new Monument("Unsung Founders Memorial", 35.913620, -79.052120, R.drawable.unsung));
        monuments.add(new Monument("Susan Williams Graham Memorial Fountain", 35.914230,
                -79.049770, R.drawable.fountain));
        monuments.add(new Monument("Memorial to Founding Trustees", 35.912540 , -79.052480, R.drawable.founders));

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
