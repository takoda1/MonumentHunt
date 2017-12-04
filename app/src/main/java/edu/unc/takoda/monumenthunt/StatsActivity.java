package edu.unc.takoda.monumenthunt;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static SQLiteDatabase db;

    private TextView stats;
    private final int numberOptions = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        db = this.openOrCreateDatabase("MyDatabase", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS SinglePlayerUntimed (date TEXT, monumentsFound INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS SinglePlayerTimed (date TEXT, monumentsFound INTEGER)");

        Spinner spinner = (Spinner)findViewById(R.id.statsSelect);
        spinner.setOnItemSelectedListener(this);

        stats = (TextView)findViewById(R.id.stats);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Cursor c;

        //hardcoded options i know its bad
        if(pos == 0){
            c = db.rawQuery("SELECT S.date, S.monumentsFound FROM SinglePlayerTimed s", null);
        }
        else{ //pos = 1 is only other option
            c = db.rawQuery("SELECT S.date, S.monumentsFound FROM SinglePlayerUntimed s", null);
        }

        c.moveToFirst();
        Log.v("ABC", c.getString(0));
        String display = "              Date              Monuments Found\n\n";
        for(int i = 0; i < numberOptions && i < c.getCount(); i++){
            display+=c.getString(0)+"               "+c.getInt(1)+"\n";
            c.moveToNext();
        }
        stats.setText(display);
    }

    public void onNothingSelected(AdapterView<?> parent){

    }

    public void back(View v){
        Intent backIntent = new Intent(this, StartMenu.class);
        startActivity(backIntent);
    }
}
