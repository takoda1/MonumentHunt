package edu.unc.takoda.monumenthunt;

        import android.os.Bundle;
        import android.view.View;

/**
 * Created by takoda on 11/27/2017.
 */

public class SingleplayerUntimed extends Game {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.notification).setVisibility(View.INVISIBLE);
        findViewById(R.id.timer).setVisibility(View.INVISIBLE);
    }

    @Override
    public void backButton(View v) {
        super.backButton(v);

    }
}
