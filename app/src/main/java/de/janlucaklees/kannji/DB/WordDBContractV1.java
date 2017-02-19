package de.janlucaklees.kannji.DB;

import android.provider.BaseColumns;

public final class WordDBContractV1 {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private WordDBContractV1() {
    }

    /* Inner class that defines the table contents */
    public static class WordDB implements BaseColumns {
        public static final String TABLE_NAME = "words";
        public static final String COLUMN_NAME_ID = "word_id";
        public static final String COLUMN_NAME_WORD = "word";
    }

    /* Inner class that defines the table contents */
    public static class WordDB_Readings implements BaseColumns {
        public static final String TABLE_NAME = "readings";
        public static final String COLUMN_NAME_ID = "reading_id";
        public static final String COLUMN_NAME_WORD_ID = "word_id";
        public static final String COLUMN_NAME_READING = "reading";
    }

    /* Inner class that defines the table contents */
    public static class WordDB_Translations implements BaseColumns {
        public static final String TABLE_NAME = "translations_en";
        public static final String COLUMN_NAME_ID = "translation_id";
        public static final String COLUMN_NAME_WORD_ID = "word_id";
        public static final String COLUMN_NAME_TRANSLATION = "translation";
    }
}