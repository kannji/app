package de.janlucaklees.kannji.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordDBHelperV1 extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WordV1.db";

    private static final String SQL_CREATE_WORD_TABLE =
            "CREATE TABLE " + WordDBContractV1.WordDB.TABLE_NAME + " (" +
                    WordDBContractV1.WordDB.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    WordDBContractV1.WordDB.COLUMN_NAME_WORD + " TINYTEXT)";

    private static final String SQL_CREATE_READINGS_TABLE =
            "CREATE TABLE " + WordDBContractV1.WordDB_Readings.TABLE_NAME + " (" +
                    WordDBContractV1.WordDB_Readings.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    WordDBContractV1.WordDB_Readings.COLUMN_NAME_WORD_ID + " INTEGER," +
                    WordDBContractV1.WordDB_Readings.COLUMN_NAME_READING + " TINYTEXT)";

    private static final String SQL_CREATE_TRANSLATIONS_TABLE =
            "CREATE TABLE " + WordDBContractV1.WordDB_Translations.TABLE_NAME + " (" +
                    WordDBContractV1.WordDB_Translations.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    WordDBContractV1.WordDB_Translations.COLUMN_NAME_WORD_ID + " INTEGER," +
                    WordDBContractV1.WordDB_Translations.COLUMN_NAME_TRANSLATION + " TINYTEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WordDBContractV1.WordDB.TABLE_NAME + ";" +
                    "DROP TABLE IF EXISTS " + WordDBContractV1.WordDB_Readings.TABLE_NAME + ";" +
                    "DROP TABLE IF EXISTS " + WordDBContractV1.WordDB_Translations.TABLE_NAME;


    public WordDBHelperV1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_WORD_TABLE);
        db.execSQL(SQL_CREATE_READINGS_TABLE);
        db.execSQL(SQL_CREATE_TRANSLATIONS_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES); // TODO move data to new table
        onCreate(db);
    }
}