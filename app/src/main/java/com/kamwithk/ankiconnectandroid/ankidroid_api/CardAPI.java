package com.kamwithk.ankiconnectandroid.ankidroid_api;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.ichi2.anki.FlashCardsContract;

import java.util.ArrayList;

public class CardAPI {
    private final ContentResolver resolver;

    public CardAPI(Context context) {
        resolver = context.getContentResolver();
    }

    public ArrayList<Long> findCards(String query){
        ArrayList<Long> cardIDs = new ArrayList<>();

        final String[] CARD_PROJECTION = {FlashCardsContract.TrueCard._ID};
        try (Cursor cursor = resolver.query(FlashCardsContract.TrueCard.CONTENT_URI, CARD_PROJECTION, query, null, null)) {
            if (cursor == null) {
                return cardIDs;
            }

            while (cursor.moveToNext()) {
                cardIDs.add(cursor.getLong(cursor.getColumnIndexOrThrow(FlashCardsContract.TrueCard._ID)));
                cursor.moveToNext();
            }

            return cardIDs;
        }
    }
}
