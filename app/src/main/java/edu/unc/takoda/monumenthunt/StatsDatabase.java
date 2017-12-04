package edu.unc.takoda.monumenthunt;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by takoda on 11/16/2017.
 */

public class StatsDatabase {
    private static SQLiteDatabase db;
    private static StatsDatabase db1;


    public StatsDatabase(){
        db=SQLiteDatabase.openOrCreateDatabase("/sdcard/db",null);

        db.execSQL("CREATE TABLE IF NOT EXISTS SinglePlayerScore (MonumentID INT PRIMARY KEY, date DATE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS SinglePlayerTimedScore (MonumentID INT, date DATE)");
        //multiplayer table

        db.execSQL("CREATE TABLE IF NOT EXISTS Monuments(ID INT PRIMARY KEY, Name STRING, Lat FLOAT, Long FLOAT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS GameEvents(ID INT PRIMARY KEY, PlayerID INT, TimeOfArrival datetime)");

        db.execSQL("CREATE TABLE IF NOT EXISTS Player(ID INT PRIMARY KEY)");

        MonumentData x=new MonumentData();

        ArrayList<Monument> monList = x.monumentList(); //new ArrayList<MonumentData>();



        for (int i=0;i< monList.size();i++ ){
            ContentValues row= new ContentValues();
            row.put("Name",monList.get(i).getName());
            row.put("Lat",monList.get(i).getLatitude());
            row.put("Long", monList.get(i).getLongitude());

            db.insert("Monuments",null,row);

            //db.execSQL("INSERT INTO Monuments (Name, Lat, Long)");
        }
        //assuming you have a list of monuments found in single


    }
    public static void init (){
        if(db1==null) {

            db1 = new StatsDatabase();


        }

    }
    public static StatsDatabase getInstance(){
        return db1;
    }




    //db= openOrCreateDatabase("MyDatabase", Context.MODE_PRIVATE,null);

}
