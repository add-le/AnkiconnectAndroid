package com.kamwithk.ankiconnectandroid.ankidroid_api;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.ichi2.anki.FlashCardsContract;
import com.ichi2.anki.api.AddContentApi;

import java.util.HashMap;
import java.util.Map;

public class ModelAPI {
    private final AddContentApi api;
    private final ContentResolver resolver;

    public ModelAPI(Context context) {
        api = new AddContentApi(context);
        resolver = context.getContentResolver();
    }

    public String[] modelNames() throws Exception {
        Map<Long, String> models = api.getModelList(0);

        if (models != null) {
            return models.values().toArray(new String[0]);
        } else {
            throw new Exception("Couldn't get model names");
        }
    }

    public Map<String, Long> modelNamesAndIds(Integer numFields) throws Exception {
        Map<Long, String> temporary = api.getModelList(numFields);
        Map<String, Long> models = new HashMap<>();

        if (temporary != null) {
            // Reverse hashmap to get entries of (Name, ID)
            for (Map.Entry<Long, String> entry : temporary.entrySet()) {
                models.put(entry.getValue(), entry.getKey());
            }

            return models;
        } else {
            throw new Exception("Couldn't get models names and IDs");
        }
    }

    public String[] modelFieldNames(Long model_id) {
        return api.getFieldList(model_id);
    }

    public Long getModelID(String modelName, Integer numFields) throws Exception {
        Map<String, Long> modelList = modelNamesAndIds(numFields);
        for (Map.Entry<String, Long> entry : modelList.entrySet()) {
            if (entry.getKey().equals(modelName)) {
                return entry.getValue(); // first model wins
            }
        }

        // Can't find model
        throw new Exception("Couldn't get model ID");
    }

    public Model getModel(Long modelId) throws Exception {
        final Uri modelUri = Uri.withAppendedPath(FlashCardsContract.Model.CONTENT_URI, Long.toString(modelId));

        try(final Cursor cursor = resolver.query(modelUri, null, null, null, null)) {
            if(cursor == null) {
                throw new Exception("Model not found");
            }

            cursor.moveToFirst();
            return new Model(
            cursor.getLong(cursor.getColumnIndexOrThrow(FlashCardsContract.Model._ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(FlashCardsContract.Model.NAME)),
            cursor.getString(cursor.getColumnIndexOrThrow(FlashCardsContract.Model.CSS)),
            cursor.getString(cursor.getColumnIndexOrThrow(FlashCardsContract.Model.FIELD_NAMES)),
            cursor.getInt(cursor.getColumnIndexOrThrow(FlashCardsContract.Model.NUM_CARDS)),
            cursor.getLong(cursor.getColumnIndexOrThrow(FlashCardsContract.Model.DECK_ID)),
            cursor.getInt(cursor.getColumnIndexOrThrow(FlashCardsContract.Model.SORT_FIELD_INDEX)),
            cursor.getInt(cursor.getColumnIndexOrThrow(FlashCardsContract.Model.TYPE)),
            cursor.getString(cursor.getColumnIndexOrThrow(FlashCardsContract.Model.LATEX_POST)),
            cursor.getString(cursor.getColumnIndexOrThrow(FlashCardsContract.Model.LATEX_PRE))
            );
        }
    }

    public ModelQuestionAnswer getModelQuestionAnswer(Long modelId) throws Exception {
        Uri modelTemplateUri = Uri.withAppendedPath(FlashCardsContract.Model.CONTENT_URI, Long.toString(modelId));
        modelTemplateUri = Uri.withAppendedPath(modelTemplateUri, "templates");

        final String[] MODEL_TEMPLATE_PROJECTION = {FlashCardsContract.CardTemplate.QUESTION_FORMAT, FlashCardsContract.CardTemplate.ANSWER_FORMAT};

        try (final Cursor cursor = resolver.query(modelTemplateUri, MODEL_TEMPLATE_PROJECTION, null, null, null)) {
            if (cursor == null) {
                throw new Exception("Model template not found");
            }

            cursor.moveToFirst();
            return new ModelQuestionAnswer(
                    cursor.getString(cursor.getColumnIndexOrThrow(FlashCardsContract.CardTemplate.ANSWER_FORMAT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FlashCardsContract.CardTemplate.QUESTION_FORMAT))
            );
        }
    }

    static class ModelQuestionAnswer {
        private final String answer;
        private final String question;

        public ModelQuestionAnswer(String answer, String question) {
            this.answer = answer;
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public String getQuestion() {
            return question;
        }
    }

    static class Model {
        private final Long _ID;
        private final String NAME;
        private final String CSS;
        private final String FIELD_NAMES;
        private final Integer NUM_CARDS;
        private final Long DECK_ID;
        private final Integer SORT_FIELD_INDEX;
        private final Integer TYPE;
        private final String LATEX_POST;
        private final String LATEX_PRE;

        public Model(Long _ID, String NAME, String CSS, String FIELD_NAMES, Integer NUM_CARDS, Long DECK_ID, Integer SORT_FIELD_INDEX, Integer TYPE, String LATEX_POST, String LATEX_PRE) {
            this._ID = _ID;
            this.NAME = NAME;
            this.CSS = CSS;
            this.FIELD_NAMES = FIELD_NAMES;
            this.NUM_CARDS = NUM_CARDS;
            this.DECK_ID = DECK_ID;
            this.SORT_FIELD_INDEX = SORT_FIELD_INDEX;
            this.TYPE = TYPE;
            this.LATEX_POST = LATEX_POST;
            this.LATEX_PRE = LATEX_PRE;

        }

        public Long get_ID() {
            return _ID;
        }

        public String getNAME() {
            return NAME;
        }

        public String getCSS() {
            return CSS;
        }

        public String getFIELD_NAMES() {
            return FIELD_NAMES;
        }

        public Integer getNUM_CARDS() {
            return NUM_CARDS;
        }

        public Long getDECK_ID() {
            return DECK_ID;
        }

        public Integer getSORT_FIELD_INDEX() {
            return SORT_FIELD_INDEX;
        }

        public Integer getTYPE() {
            return TYPE;
        }

        public String getLATEX_POST() {
            return LATEX_POST;
        }

        public String getLATEX_PRE() {
            return LATEX_PRE;
        }
    }


}
