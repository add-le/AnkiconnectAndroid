package com.kamwithk.ankiconnectandroid.ankidroid_api;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.ichi2.anki.FlashCardsContract;
import com.ichi2.anki.api.AddContentApi;

import java.util.ArrayList;
import java.util.Map;

public class CardAPI {
    private final ContentResolver resolver;

    public CardAPI(Context context) {
        resolver = context.getContentResolver();
    }

    public ArrayList<Long> findCards(String query){
        final ArrayList<Long> cardIDs = new ArrayList<>();
        final String[] CARD_PROJECTION = {FlashCardsContract.TrueCard._ID};

        try (final Cursor cursor = resolver.query(FlashCardsContract.TrueCard.CONTENT_URI, CARD_PROJECTION, query, null, null)) {
            if (cursor == null) {
                return cardIDs;
            }

            while (cursor.moveToNext()) {
                cardIDs.add(cursor.getLong(cursor.getColumnIndexOrThrow(FlashCardsContract.TrueCard._ID)));
            }

            return cardIDs;
        }
    }

    static class CardInfoField {
        private final String value;
        private final Long order;

        public CardInfoField(String value, Long order) {
            this.value = value;
            this.order = order;
        }

        public String getValue() {
            return value;
        }

        public Long getOrder() {
            return order;
        }
    }

    static class CardInfo {
        private final String answer;
        private final String question;
        private final String deckName;
        private final String modelName;
        private final Long fieldOrder;
        private final Map<String, CardInfoField> fields;
        private final String css;
        private final Long cardId;
        private final Long interval;
        private final Long note;
        private final Long ord;
        private final Long type;
        private final Long queue;
        private final Long due;
        private final Long reps;
        private final Long lapses;
        private final Long left;
        private final Long mod;

        public CardInfo(
            String answer,
            String question,
            String deckName,
            String modelName,
            Long fieldOrder,
            Map<String, CardInfoField> fields,
            String css,
            Long cardId,
            Long interval,
            Long note,
            Long ord,
            Long type,
            Long queue,
            Long due,
            Long reps,
            Long lapses,
            Long left,
            Long mod
        ) {
            this.answer = answer;
            this.question = question;
            this.deckName = deckName;
            this.modelName = modelName;
            this.fieldOrder = fieldOrder;
            this.fields = fields;
            this.css = css;
            this.cardId = cardId;
            this.interval = interval;
            this.note = note;
            this.ord = ord;
            this.type = type;
            this.queue = queue;
            this.due = due;
            this.reps = reps;
            this.lapses = lapses;
            this.left = left;
            this.mod = mod;
        }

        public String getAnswer() {
            return answer;
        }

        public String getQuestion() {
            return question;
        }

        public String getDeckName() {
            return deckName;
        }

        public String getModelName() {
            return modelName;
        }

        public Long getFieldOrder() {
            return fieldOrder;
        }

        public Map<String, CardInfoField> getFields() {
            return fields;
        }

        public String getCss() {
            return css;
        }

        public Long getCardId() {
            return cardId;
        }

        public Long getInterval() {
            return interval;
        }

        public Long getNote() {
            return note;
        }

        public Long getOrd() {
            return ord;
        }

        public Long getType() {
            return type;
        }

        public Long getQueue() {
            return queue;
        }

        public Long getDue() {
            return due;
        }

        public Long getReps() {
            return reps;
        }

        public Long getLapses() {
            return lapses;
        }

        public Long getLeft() {
            return left;
        }

        public Long getMod() {
            return mod;
        }
    }
}
