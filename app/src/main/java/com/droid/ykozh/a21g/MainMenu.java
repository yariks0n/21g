package com.droid.ykozh.a21g;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainMenu extends SurfaceView implements Runnable{

    private static final String TAG = "MainMenu";
    private static final String CARDS_FOLDER = "cards";
    volatile boolean playing = true;
    Thread gameThread = null;
    final Random random = new Random();
    private int anim_speed = 3;

    private int screenX;
    private int screenY;
    private int cardWidth;
    private int cardHeight;

    // Game objects
    private String[] cardNames;
    private ArrayList<Card> mCards = new ArrayList<>();
    private ArrayList<Card> mCardsDraw1 = new ArrayList<>();
    private ArrayList<Card> mCardsDraw2 = new ArrayList<>();
    private ArrayList<Card> mCardsDraw3 = new ArrayList<>();
    private ArrayList<Card> mCardsDraw4 = new ArrayList<>();
    private ArrayList<Card> mCardsDraw5 = new ArrayList<>();
    private ArrayList<Card> mCardsDraw6 = new ArrayList<>();

    private InputStream inputStream;

    // For drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    //Anim
    int offset;

    int yGroup1;
    int yGroup2;
    int yGroup3;
    int yGroup4;
    int yGroup5;
    int yGroup6;

    public MainMenu(Context context,int x, int y) {
        super(context);

        gameThread = new Thread(this);
        gameThread.start();

        // Initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        AssetManager assetManager = context.getAssets();
        AssetFileDescriptor descriptor;

        try {
            cardNames = assetManager.list(CARDS_FOLDER);
            Log.i(TAG, "Found " + cardNames.length + " cards");
        } catch (IOException ioe) {
            Log.e(TAG, "Could not list assets", ioe);
            return;
        }

        for (String filename : cardNames) {
            String assetPath = CARDS_FOLDER + "/" + filename;

            try {
                Card card = new Card(context,assetPath);
                mCards.add(card);
                inputStream = context.getAssets().open(assetPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        screenX = x;
        screenY = y;

        cardWidth = screenX / 12;
        cardHeight = screenY / 5;

        for(int i = 0; i <= 14; i++){
            mCardsDraw1.add(mCards.get(random.nextInt(mCards.size())));
            mCardsDraw2.add(mCards.get(random.nextInt(mCards.size())));
            mCardsDraw3.add(mCards.get(random.nextInt(mCards.size())));
            mCardsDraw4.add(mCards.get(random.nextInt(mCards.size())));
            mCardsDraw5.add(mCards.get(random.nextInt(mCards.size())));
            mCardsDraw6.add(mCards.get(random.nextInt(mCards.size())));
        }

        yGroup1 = -cardHeight;
        yGroup2 = 0;
        yGroup3 = cardHeight;
        yGroup4 = cardHeight*2;
        yGroup5 = cardHeight*3;
        yGroup6 = cardHeight*4;
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    private void update(){

        if(mCards.size() > 20) {
            yGroup1+=anim_speed;
            yGroup2+=anim_speed;
            yGroup3+=anim_speed;
            yGroup4+=anim_speed;
            yGroup5+=anim_speed;
            yGroup6+=anim_speed;


            if (yGroup1 > screenY) {
                yGroup1 = -cardHeight;
                mCardsDraw1.clear();
                for (int i = 0; i <= 14; i++) {
                    mCardsDraw1.add(mCards.get(random.nextInt(mCards.size() - 1)));
                }
            }

            if (yGroup2 > screenY) {
                yGroup2 = -cardHeight;
                mCardsDraw2.clear();
                for (int i = 0; i <= 14; i++) {
                    mCardsDraw2.add(mCards.get(random.nextInt(mCards.size() - 1)));
                }
            }

            if (yGroup3 > screenY) {
                yGroup3 = -cardHeight;
                mCardsDraw3.clear();
                for (int i = 0; i <= 14; i++) {
                    mCardsDraw3.add(mCards.get(random.nextInt(mCards.size() - 1)));
                }
            }

            if (yGroup4 > screenY) {
                yGroup4 = -cardHeight;
                mCardsDraw4.clear();
                for (int i = 0; i <= 14; i++) {
                    mCardsDraw4.add(mCards.get(random.nextInt(mCards.size() - 1)));
                }
            }

            if (yGroup5 > screenY) {
                yGroup5 = -cardHeight;
                mCardsDraw5.clear();
                for (int i = 0; i <= 14; i++) {
                    mCardsDraw5.add(mCards.get(random.nextInt(mCards.size() - 1)));
                }
            }
            if (yGroup6 > screenY) {
                yGroup6 = -cardHeight;
                mCardsDraw6.clear();
                for (int i = 0; i <= 14; i++) {
                    mCardsDraw6.add(mCards.get(random.nextInt(mCards.size() - 1)));
                }
            }
        }

    }

    private void draw(){

        if (ourHolder.getSurface().isValid()) {
            //First we lock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            // Rub out the last frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            drawLine(canvas,paint,mCardsDraw1,yGroup1);
            drawLine(canvas,paint,mCardsDraw2,yGroup2);
            drawLine(canvas,paint,mCardsDraw3,yGroup3);
            drawLine(canvas,paint,mCardsDraw4,yGroup4);
            drawLine(canvas,paint,mCardsDraw5,yGroup5);
            drawLine(canvas,paint,mCardsDraw6,yGroup6);

            // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawLine(Canvas canvas,Paint paint, ArrayList<Card> cards, int y){
        int countGroup = 0;
        int posXGroup = 0;
        for (Card card : cards) {
            Bitmap newBitmap = Bitmap.createScaledBitmap(card.getBitmap(), cardWidth, cardHeight, false);
            canvas.drawBitmap(newBitmap, posXGroup, y, paint);
            posXGroup = cardWidth*countGroup;
            countGroup++;
        }
    }

    private void control(){
        try {
            gameThread.sleep(1);
        } catch (InterruptedException e) {

        }
    }

    // Clean up our thread if the game is interrupted or the player quits
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {

        }
    }

    // Make a new thread and start it
    // Execution moves to our R
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}