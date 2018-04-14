package com.droid.ykozh.a21g;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.LinearLayout;

public class GameActivity extends AppCompatActivity {

    private Game mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mGame = new Game(this,size.x,size.y);
        LinearLayout gameLayout = (LinearLayout)findViewById(R.id.gameLayout);
        gameLayout.addView(mGame);
    }
}
