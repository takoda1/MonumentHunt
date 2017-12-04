package edu.unc.takoda.monumenthunt;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

/**
 * Created by takoda on 11/27/2017.
 */

public class SingleplayerTimed extends Game {

    private TextView timeView;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.notification).setVisibility(View.INVISIBLE);
        timeView = (TextView) findViewById(R.id.timer);
        
        new CountDownTimer(3600000, 1000){
            public void onTick(long millisUntilFinished) {
                timeView.setText("Time remaining: " + (millisUntilFinished / 1000));
            }

            public void onFinish(){

            }
        }.start();


    }

    @Override
    public void backButton(View v) {
        super.backButton(v);
    }


    private void saveData(){

    }
}
