package de.janlucaklees.kannji.services.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import de.janlucaklees.kannji.datatypes.Kanji;

public class WordDBAdministrationInterface implements WordDBInterfaceV1 {

	private SQLiteDatabase _db;

	public WordDBAdministrationInterface() {
		WordDBHelperV1 wordDBHelperV1 = new WordDBHelperV1();
		_db = wordDBHelperV1.getWritableDatabase();
	}

	@Override
	public void storeWord( String word, String reading, String translation ) {

		long newWordID = insertWord( word );

		insertReading( newWordID, reading );
		insertTranslation( newWordID, translation );
	}

	@Override
	public void storeWord( String word, List<String> readings, List<String> translations ) {
		long newWordID = insertWord( word );

		for ( String reading : readings ) {
			insertReading( newWordID, reading );
		}

		for ( String translation : translations ) {
			insertTranslation( newWordID, translation );
		}
	}

	@Override
	public List<Word> getAllWords() {
		String[] wordProjection = {
				WordDBContractV1.WordDB.COLUMN_NAME_ID,
				WordDBContractV1.WordDB.COLUMN_NAME_WORD,
		};

		Cursor wordCursor = _db.query(
				WordDBContractV1.WordDB.TABLE_NAME,                     // The table to query
				wordProjection,                               // The columns to return
				null,                                // The columns for the WHERE clause
				null,                            // The values for the WHERE clause
				null,                                     // don't group the rows
				null,                                     // don't filter by row groups
				null                                 // The sort order
		);

		List<Word> words = new ArrayList<>();
		while ( wordCursor.moveToNext() ) {
			long wordId = wordCursor.getLong(
					wordCursor.getColumnIndexOrThrow( WordDBContractV1.WordDB.COLUMN_NAME_ID ) );

			String word = wordCursor.getString(
					wordCursor.getColumnIndexOrThrow( WordDBContractV1.WordDB.COLUMN_NAME_WORD ) );

			List<String> readings = getReadingsForWord( wordId );

			List<String> translations = getTranslationsForWord( wordId );

			words.add( new Word( wordId, word, readings, translations ) );
		}
		wordCursor.close();

		return words;
	}

	@Override
	public List<Kanji> getAllKanji() {
		return null;
	}

	@Override
	public Kanji getKanji( long kanjiId ) {
		return null;
	}

	private List<String> getTranslationsForWord( long wordId ) {
		String[] wordProjection = {
				WordDBContractV1.WordDB_Translations.COLUMN_NAME_TRANSLATION,
		};

		String selection = WordDBContractV1.WordDB_Translations.COLUMN_NAME_WORD_ID + " = ?";
		String[] selectionArgs = {Long.toString( wordId )};

		Cursor translationCursor = _db.query(
				WordDBContractV1.WordDB_Translations.TABLE_NAME,                     // The table to query
				wordProjection,                               // The columns to return
				selection,                                // The columns for the WHERE clause
				selectionArgs,                            // The values for the WHERE clause
				null,                                     // don't group the rows
				null,                                     // don't filter by row groups
				null                                 // The sort order
		);

		List<String> translations = new ArrayList<>();
		while ( translationCursor.moveToNext() ) {
			String translation = translationCursor.getString(
					translationCursor.getColumnIndexOrThrow( WordDBContractV1.WordDB_Translations.COLUMN_NAME_TRANSLATION ) );

			translations.add( translation );
		}
		translationCursor.close();

		return translations;
	}

	private List<String> getReadingsForWord( long wordId ) {
		String[] wordProjection = {
				WordDBContractV1.WordDB_Readings.COLUMN_NAME_READING,
		};

		String selection = WordDBContractV1.WordDB_Readings.COLUMN_NAME_WORD_ID + " = ?";
		String[] selectionArgs = {Long.toString( wordId )};

		Cursor readingsCursor = _db.query(
				WordDBContractV1.WordDB_Readings.TABLE_NAME,                     // The table to query
				wordProjection,                               // The columns to return
				selection,                                // The columns for the WHERE clause
				selectionArgs,                            // The values for the WHERE clause
				null,                                     // don't group the rows
				null,                                     // don't filter by row groups
				null                                 // The sort order
		);

		List<String> readings = new ArrayList<>();
		while ( readingsCursor.moveToNext() ) {
			String reading = readingsCursor.getString(
					readingsCursor.getColumnIndexOrThrow( WordDBContractV1.WordDB_Readings.COLUMN_NAME_READING ) );

			readings.add( reading );
		}
		readingsCursor.close();

		return readings;
	}

	private long insertWord( String word ) {
		ContentValues wordValues = new ContentValues();
		wordValues.put( WordDBContractV1.WordDB.COLUMN_NAME_WORD, word );

		return _db.insert( WordDBContractV1.WordDB.TABLE_NAME, null, wordValues );
	}

	private long insertReading( long wordID, String reading ) {
		ContentValues readingValues = new ContentValues();
		readingValues.put( WordDBContractV1.WordDB_Readings.COLUMN_NAME_WORD_ID, wordID );
		readingValues.put( WordDBContractV1.WordDB_Readings.COLUMN_NAME_READING, reading );

		return _db.insert( WordDBContractV1.WordDB_Readings.TABLE_NAME, null, readingValues );
	}

	private long insertTranslation( long newWordID, String translation ) {
		ContentValues translationValues = new ContentValues();
		translationValues.put( WordDBContractV1.WordDB_Translations.COLUMN_NAME_WORD_ID, newWordID );
		translationValues.put( WordDBContractV1.WordDB_Translations.COLUMN_NAME_TRANSLATION, translation );

		return _db.insert( WordDBContractV1.WordDB_Translations.TABLE_NAME, null, translationValues );
	}
}
