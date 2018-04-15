package com.droid.ykozh.a21g;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";
    private Game mGame;

    private Button nextBtn, enoughBtn, playBtn;
    private LinearLayout playerControl;
    private LinearLayout gameControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mGame = new Game(this, size.x, size.y);
        LinearLayout gameLayout = (LinearLayout) findViewById(R.id.gameLayout);
        gameLayout.addView(mGame);
        mGame.init();

        playerControl = (LinearLayout) findViewById(R.id.playerControl);
        gameControl = (LinearLayout) findViewById(R.id.gameControl);
        gameControl.setVisibility(View.INVISIBLE);

        nextBtn = (Button) findViewById(R.id.next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mGame.gameEnd) {
                    if (mGame.mCards.size() > 0) {
                        Card card = mGame.mCards.get(mGame.mCards.size() - 1);
                        mGame.mCards.remove(mGame.mCards.size() - 1);

                        if (mGame.player1Game) {
                            mGame.setPlayer1Score(card.getScore());
                        } else if (mGame.player2Game) {
                            mGame.setPlayer2Score(card.getScore());
                        }

                        mGame.drawCards.add(card);
                        mGame.updateView();
                    }
                }
                mGame.checkGameState();
                if(mGame.gameEnd){
                    playerControl.setVisibility(View.INVISIBLE);
                    gameControl.setVisibility(View.VISIBLE);
                }
            }
        });

        enoughBtn = (Button) findViewById(R.id.enough);
        enoughBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mGame.gameEnd) {
                    if (mGame.player1Game) {
                        mGame.player1Game = false;
                        mGame.player2Game = true;
                        mGame.drawCards.clear();
                    }else if (mGame.player2Game) {
                        mGame.player1Game = false;
                        mGame.player2Game = false;
                        mGame.gameEnd = true;
                        playerControl.setVisibility(View.INVISIBLE);
                        gameControl.setVisibility(View.VISIBLE);
                    }
                }
                mGame.updateView();
                mGame.checkGameState();
            }
        });

        playBtn = (Button) findViewById(R.id.playAgain);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameControl.setVisibility(View.INVISIBLE);
                playerControl.setVisibility(View.VISIBLE);
                mGame.init();
                mGame.updateView();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mGame.init();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGame.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGame.release();
    }
}
