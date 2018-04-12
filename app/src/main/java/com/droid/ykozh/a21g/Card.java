package com.droid.ykozh.a21g;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class Card {
    private Bitmap bitmap;
    private String mAssetPath;
    private String mName;
    private int mScore;

    private int x;
    private int y;
    private int maxY;
    private int minY;

    public Card(Context context,String assetPath) {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        mName = filename.replace(".gif", "");

        String[] score = mName.split("-");
        String scoreText = score[score.length - 1];
        mScore = Integer.parseInt(scoreText);

        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(assetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(inputStream);
    }

    public String getName() {
        return mName;
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public int getScore() {
        return mScore;
    }

    public void update() {
        y += 1;
    }

    public int getY(){
        return y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
