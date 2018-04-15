package com.droid.ykozh.a21g;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActive";

    public MainMenu mMainMenu;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cards.setCards(this);

        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        mMainMenu = new MainMenu(this,size.x,size.y); // создаём mMainMenu

        LinearLayout mainMenuAnimLayout = (LinearLayout) findViewById(R.id.mainMenuAnimLayout); // находим gameLayout
        mainMenuAnimLayout.addView(mMainMenu);

        startButton = (Button)findViewById(R.id.start);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GameActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "pause");
        mMainMenu.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "stop");
        mMainMenu.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "resume");
        mMainMenu.resume();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "start");
        mMainMenu.resume();
    }

}
