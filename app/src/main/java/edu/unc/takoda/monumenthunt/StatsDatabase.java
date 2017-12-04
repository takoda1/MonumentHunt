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
    private static SQLiteDatabase db;


    public StatsDatabase(){
        db = SQLiteDatabase.openOrCreateDatabase("MyDatabase", null);

        db.execSQL("CREATE TABLE IF NOT EXISTS SinglePlayerUntimed (date TEXT, monumentsFound INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS SinglePlayerTimed (date TEXT, monumentsFound INTEGER)");
    }

    public void storeUnitmedData(int monumentsFound){
        db.execSQL("INSERT INTO SinglePlayerUntimed VALUES(datetime('now'), '" + monumentsFound +"'");
    }

    public void storeTimedData(int monumentsFound){
        db.execSQL("INSERT INTO SinglePlayerTimed VALUES(datetime('now'), '" + monumentsFound +"'");
    }

    public Cursor getUntimedDataCursor(){
        return db.rawQuery("SELECT S.date, S.monumentsFound FROM SinglePlayerUntimed", null);
    }

    public Cursor getTimedDataCursor(){
        return db.rawQuery("SELECT S.date, S.monumentsFound FROM SinglePlayerTimed", null);
    }
}
