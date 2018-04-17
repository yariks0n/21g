package com.droid.ykozh.a21g;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Cards {

    private static ArrayList<Card> mCards = new ArrayList<>();
    private static String[] cardNames;

    public static void setCards(Context context) {
        AssetManager assetManager = context.getAssets();
        try {
            cardNames = assetManager.list(Settings.CARDS_FOLDER);
        } catch (IOException ioe) {
            return;
        }
        for (String filename : cardNames) {
            String assetPath = Settings.CARDS_FOLDER + "/" + filename;
            Card card = new Card(context, assetPath);
            mCards.add(card);
        }
    }

    public static ArrayList<Card> getCards() {
        ArrayList<Card> newCards = new ArrayList<>();
        for (Card card : mCards) {
            newCards.add(card);
        }
        return newCards;
    }

    public static Card getRandomCard() {
        final Random random = new Random();
        int pos = random.nextInt(getCards().size()-1);
        return getCards().get(pos);
    }
}
