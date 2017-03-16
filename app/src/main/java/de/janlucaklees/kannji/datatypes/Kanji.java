package de.janlucaklees.kannji.datatypes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Kanji extends LearningEntry {

	// JSON keys
	public static final String KANJI_ID = "kanji_id";
	public static final String LITERAL = "literal";
	public static final String STROKE_COUNT = "stroke_count";

	public static final String READINGS = "readings";
	public static final String READINGS_TYPE = "type";
	public static final String READINGS_TYPE_ONYOMI = "onyomi";
	public static final String READINGS_TYPE_KUNYOMI = "kunyomi";
	public static final String READINGS_TYPE_NANORI = "nanori";
	public static final String READINGS_READING = "reading";

	public static final String MEANINGS = "meanings";
	public static final String MEANINGS_MEANING = "meaning";
	public static final String MEANINGS_LANGUAGE = "language";

	// Kanji Data
	private long _id;
	private Character _literal;
	private byte _stroke_count;
	private List<String> _onReadings;
	private List<String> _kunReadings;
	private List<String> _nanoriReadings;

	private Map<String, Set<String>> _meanings;

	// TODO factory schema implementieren
	public Kanji( JSONObject kanjiJson ) throws JSONException {
		// extract basic information
		_id = kanjiJson.getLong( KANJI_ID );
		_literal = kanjiJson.getString( LITERAL ).toCharArray()[ 0 ];
		_stroke_count = (byte) kanjiJson.getInt( STROKE_COUNT );

		// extract readings
		extractReadings( kanjiJson );

		// extract meanings
		extractMeanings( kanjiJson );
	}

	private void extractReadings( JSONObject kanjiJson ) throws JSONException {
		_onReadings = new ArrayList<>();
		_kunReadings = new ArrayList<>();
		_nanoriReadings = new ArrayList<>();

		JSONObject readingsJson = kanjiJson.getJSONObject( READINGS );

		Iterator<String> readingsIterator = readingsJson.keys();
		while ( readingsIterator.hasNext() ) {
			String key = readingsIterator.next();

			JSONObject readingJson = readingsJson.getJSONObject( key );

			switch ( readingJson.getString( READINGS_TYPE ) ) {

				case READINGS_TYPE_ONYOMI:
					_onReadings.add( readingJson.getString( READINGS_READING ) );
					break;

				case READINGS_TYPE_KUNYOMI:
					_kunReadings.add( readingJson.getString( READINGS_READING ) );
					break;

				case READINGS_TYPE_NANORI:
					_nanoriReadings.add( readingJson.getString( READINGS_READING ) );
					break;
			}
		}
	}

	private void extractMeanings( JSONObject kanjiJson ) throws JSONException {
		_meanings = new HashMap<>();

		JSONObject meaningsJson = kanjiJson.getJSONObject( MEANINGS );

		Iterator<String> meaningsIterator = meaningsJson.keys();
		while ( meaningsIterator.hasNext() ) {
			String key = meaningsIterator.next();

			JSONObject meaningJson = meaningsJson.getJSONObject( key );

			String meaning = meaningJson.getString( MEANINGS_MEANING );
			String language = meaningJson.getString( MEANINGS_LANGUAGE );

			if ( !_meanings.containsKey( language ) ) {
				_meanings.put( language, new HashSet<String>() );
			}

			_meanings.get( language ).add( meaning );
		}
	}

	public long getId() {
		return _id;
	}

	public Character getLiteral() {
		return _literal;
	}

	public byte getStrokeCount() {
		return _stroke_count;
	}

	public List<String> getOnReadings() {
		return _onReadings;
	}

	public List<String> getKunReadings() {
		return _kunReadings;
	}

	public List<String> getNanoriReadings() {
		return _nanoriReadings;
	}

	public Map<String, Set<String>> getMeanings() {
		return _meanings;
	}
}
