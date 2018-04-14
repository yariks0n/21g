package com.droid.ykozh.a21g;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class Game extends SurfaceView {

    private static final String TAG = "GAME";
    private ArrayList<Card> mCards = new ArrayList<>();

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public Game(Context context, int x, int y) {
        super(context);
        mCards = Cards.getCards();

        for(Card card: mCards){
            Log.i(TAG,card.getName());
        }

    }
}
