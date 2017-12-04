package edu.unc.takoda.monumenthunt;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import java.sql.Date;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by takoda on 11/16/2017.
 */

public class StatsDatabase {
    private SQLiteDatabase db;


    public StatsDatabase(){

    }

    public void openDatabase(){
        db = SQLiteDatabase.openOrCreateDatabase("MyDatabase", null);

        db.execSQL("CREATE TABLE IF NOT EXISTS SinglePlayerUntimed (date DATE, monumentsFound INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS SinglePlayerTimed (date DATE, monumentsFound INTEGER)");
    }

    public void storeUnitmedData(Date date, int monumentsFound){
        db.execSQL("INSERT INTO SinglePlayerUntimed VALUES('" + date + "', '" + monumentsFound +"'");
    }

    public void storeTimedData(Date date, int monumentsFound){
        db.execSQL("INSERT INTO SinglePlayerTimed VALUES('" + date + "', '" + monumentsFound +"'");
    }

    
}
