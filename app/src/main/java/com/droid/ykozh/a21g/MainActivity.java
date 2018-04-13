package com.droid.ykozh.a21g;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private MainMenu mMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        MainMenu mMainMenu = new MainMenu(this,size.x,size.y); // создаём mMainMenu

        LinearLayout mainMenuAnimLayout = (LinearLayout) findViewById(R.id.mainMenuAnimLayout); // находим gameLayout
        mainMenuAnimLayout.addView(mMainMenu);

    }

}
