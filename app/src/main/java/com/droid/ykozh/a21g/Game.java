package com.droid.ykozh.a21g;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.InputStream;
import java.util.ArrayList;

public class Game extends SurfaceView {


    private String[] cardNames;
    private ArrayList<Card> mCards = new ArrayList<>();
    private InputStream inputStream;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public Game(Context context, int x, int y){
        super(context);

    }

}
