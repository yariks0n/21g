package com.droid.ykozh.a21g;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;

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
        return mCards;
    }

}
