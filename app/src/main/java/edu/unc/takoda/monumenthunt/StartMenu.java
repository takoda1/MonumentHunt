package edu.unc.takoda.monumenthunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
    }

    public void startGame(View v){
        Intent gameIntent = new Intent();
        switch(v.getId()){
            case R.id.singleRelax:
                gameIntent = new Intent(this, SingleplayerUntimed.class);
                break;
        }
        gameIntent.putExtra("Gamemode", v.getId());
        startActivity(gameIntent);
    }

    public void statsButton(View v){
        Intent statsIntent = new Intent(this, StatsActivity.class);
        startActivity(statsIntent);
    }
}
