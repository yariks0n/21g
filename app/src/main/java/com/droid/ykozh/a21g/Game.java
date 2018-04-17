package com.droid.ykozh.a21g;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;

public class Game extends View {

    private static final String TAG = "GAME";
    public ArrayList<Card> mCards = new ArrayList<>();
    public ArrayList<Card> drawCards = new ArrayList<>();

    private Paint paint, mBackgroundPaint;
    private Paint textPaintPlayer1;
    private Paint textPaintPlayer2;
    private Paint textWin;

    private int screenX, screenY, cardWidth, cardHeight;
    private int player1Score = 0;
    private int player2Score = 0;

    public boolean gameEnd = false;
    public boolean player1Game = true;
    public boolean player2Game = false;
    public boolean player1Win = false;
    public boolean player2Win = false;

    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    public Game(Context context, int x, int y) {
        super(context);
        screenX = x;
        screenY = y;
        cardWidth = (int)screenX / 5;
        cardHeight = (int)(screenY / 2.2);
        paint = new Paint();
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(Color.WHITE);
        textPaintPlayer1 = new Paint();
        textPaintPlayer1.setColor(Color.BLACK);
        textPaintPlayer1.setTextAlign(Paint.Align.LEFT);
        textPaintPlayer1.setTextSize(35);
        textPaintPlayer2 = new Paint();
        textPaintPlayer2.setColor(Color.BLACK);
        textPaintPlayer2.setTextAlign(Paint.Align.RIGHT);
        textPaintPlayer2.setTextSize(35);
        textWin = new Paint();
        textWin.setColor(Color.BLACK);
        textWin.setTextAlign(Paint.Align.CENTER);
        textWin.setTextSize(65);

        mAssets = context.getAssets();
        mSoundPool = new SoundPool(Settings.MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();

    }

    private void loadSounds() {
        String[] soundNames;
        try {
            soundNames = mAssets.list(Settings.SOUNDS_FOLDER);
        } catch (IOException ioe) {
            return;
        }

        for (String filename : soundNames) {
            try {
                String assetPath = Settings.SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                Log.i(TAG, "File sound: " + filename);
                load(sound);
                mSounds.add(sound);
            } catch (IOException ioe) {
                Log.e(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public void init(){
        mCards = Cards.getCards();
        shuffle(mCards);
        drawCards.clear();
        gameEnd = false;
        player1Game = true;
        player2Game = false;
        player1Win = false;
        player2Win = false;
        player1Score = 0;
        player2Score = 0;
    }

    public void playSound(String soundName) {
        for(Sound sound: mSounds) {
            if (soundName.equals(sound.getName())){
                Integer soundId = sound.getSoundId();
                if (soundId == null) {
                    return;
                }
                mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
            }
        }
    }

    public void release() {
        mSoundPool.release();
    }

    public void updateView() {
        invalidate();
    }

    public void setPlayer1Score(int score){
        player1Score += score;
    }

    public int getPlayer1Score(){
        return player1Score;
    }

    public void setPlayer2Score(int score){
        player2Score += score;
    }

    public int getPlayer2Score(){
        return player2Score;
    }

    public void checkGameState(){
        if(player1Score > 21){
            player2Win = true;
            gameEnd = true;
        }else if(player1Score == 21){
            player1Win = true;
            gameEnd = true;
        }else if(player2Score > 21){
            player1Win = true;
            gameEnd = true;
        }else if(player2Score == 21){
            player2Win = true;
            gameEnd = true;
        }else if(!player1Game && !player2Game){
            if(player1Score > player2Score){
                player1Win = true;
            }else{
                player2Win = true;
            }
        }

        if(gameEnd){
            playSound("applause");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackgroundPaint);

        int step = cardWidth/3;
        int countDrawCards = drawCards.size();
         /*Log.i(TAG,"screenX: "+screenX);
        Log.i(TAG,"countDrawCards: "+countDrawCards);
        Log.i(TAG,"countDrawCards: "+(cardWidth + step*countDrawCards-1));

        Log.i(TAG,"countDrawCards: "+(screenX-(cardWidth + step*countDrawCards-1)));*/
        int left = (screenX-(cardWidth + step*countDrawCards-1))/2 - step/2;
        int top = (int)(screenY/4.7);

        for (Card card : drawCards){
            left+=step;
            Bitmap newBitmap = Bitmap.createScaledBitmap(card.getBitmap(), cardWidth, cardHeight, false);
            canvas.drawBitmap(newBitmap, left, top, paint);
        }

        if(player1Game || gameEnd)
            canvas.drawText("Player 1: "+getPlayer1Score(),30,60,textPaintPlayer1);

        if(player2Game || gameEnd)
            canvas.drawText("Player 2: "+getPlayer2Score(),screenX-30,60,textPaintPlayer2);

        if(player1Win){
            canvas.drawText("Player1 WIN !",screenX/2,80,textWin);
        }else if(player2Win){
            canvas.drawText("Player2 WIN !",screenX/2,80,textWin);
        }

    }


}
