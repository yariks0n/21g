package com.droid.ykozh.a21g;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActive";

    public MainMenu mMainMenu;
    private Button startButton;
    private ImageView cardView;
    private boolean animAcepted;

    private ObjectAnimator scaleXAnimator;
    private ObjectAnimator scaleYAnimator;
    private ObjectAnimator scalelXAnimator;
    private ObjectAnimator scalelYAnimator;
    private AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cards.setCards(this);
        cardView = (ImageView) findViewById(R.id.card_img);
        cardView.setImageBitmap(Cards.getRandomCard().getBitmap());

        /*Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        mMainMenu = new MainMenu(this,size.x,size.y); // создаём mMainMenu

        LinearLayout mainMenuAnimLayout = (LinearLayout) findViewById(R.id.mainMenuAnimLayout); // находим gameLayout
        mainMenuAnimLayout.addView(mMainMenu);*/

        startButton = (Button)findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GameActivity.class);
                startActivity(intent);
            }
        });
        startAnimation();
    }

    private void startAnimation() {
        animAcepted = true;
        scaleXAnimator = ObjectAnimator
                .ofFloat(cardView, "scaleX", 2)
                .setDuration(3000);

        scaleYAnimator = ObjectAnimator
                .ofFloat(cardView, "scaleY", 2)
                .setDuration(3000);


        scalelXAnimator = ObjectAnimator
                .ofFloat(cardView, "scaleX", 0.1f)
                .setDuration(3000);

        scalelYAnimator = ObjectAnimator
                .ofFloat(cardView, "scaleY", 0.1f)
                .setDuration(3000);

        animatorSet = new AnimatorSet();
        animatorSet
                .play(scaleXAnimator)
                .with(scaleYAnimator)
                .before(scalelXAnimator)
                .before(scalelYAnimator);
        animatorSet.start();

        scalelYAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (animAcepted){
                    cardView.setImageBitmap(Cards.getRandomCard().getBitmap());
                    animatorSet = new AnimatorSet();
                    animatorSet
                            .play(scaleXAnimator)
                            .with(scaleYAnimator)
                            .before(scalelXAnimator)
                            .before(scalelYAnimator);
                    animatorSet.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        animAcepted = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        animAcepted = false;
    }

    public void onResume() {
        super.onResume();
        startAnimation();
    }


    /*@Override
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
    }*/

}
