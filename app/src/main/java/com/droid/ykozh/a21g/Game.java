package com.droid.ykozh.a21g;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static java.util.Collections.shuffle;

public class Game extends View {

    private static final String TAG = "GAME";
    public ArrayList<Card> mCards = new ArrayList<>();
    public ArrayList<Card> drawCards = new ArrayList<>();

    private Paint paint, mBackgroundPaint,textPaint;

    private int screenX, screenY, cardWidth, cardHeight;
    private int player1Score = 0;
    private int player2Score = 0;

    public boolean gameEnd = false;
    public boolean player1Game = true;
    public boolean player2Game = false;
    public boolean player1Win = false;
    public boolean player2Win = false;

    public Game(Context context, int x, int y) {
        super(context);
        screenX = x;
        screenY = y;
        cardWidth = screenX / 10;
        cardHeight = screenY / 4;
        paint = new Paint();
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(55);
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackgroundPaint);

        int step = 50;
        int countDrawCards = drawCards.size();
        int left = screenX/2 - cardWidth - step/4*(countDrawCards-1);
        int top = screenY/3;

        for (Card card : drawCards){
            left+=step;
            Bitmap newBitmap = Bitmap.createScaledBitmap(card.getBitmap(), cardWidth, cardHeight, false);
            canvas.drawBitmap(newBitmap, left, top, paint);
        }

        if(player1Game || gameEnd)
            canvas.drawText("Player 1: "+getPlayer1Score(),30,60,textPaint);

        if(player2Game || gameEnd)
            canvas.drawText("Player 2: "+getPlayer2Score(),30,170,textPaint);

        if(player1Win){
            canvas.drawText("Player1 WIN !",screenX/2-170,200,textPaint);
        }else if(player2Win){
            canvas.drawText("Player2 WIN !",screenX/2-170,200,textPaint);
        }

    }


}
