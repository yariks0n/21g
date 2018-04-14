package com.droid.ykozh.a21g;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Game extends SurfaceView {


    private String[] cardNames;
    private ArrayList<Card> mCards = new ArrayList<>();
    private InputStream inputStream;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public Game(Context context, int x, int y) {
        super(context);

        AssetManager assetManager = context.getAssets();

        try {
            cardNames = assetManager.list(Settings.CARDS_FOLDER);
        } catch (IOException ioe) {
            return;
        }

        for (String filename : cardNames) {
            String assetPath = Settings.CARDS_FOLDER + "/" + filename;

            try {
                Card card = new Card(context, assetPath);
                mCards.add(card);
                inputStream = context.getAssets().open(assetPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
