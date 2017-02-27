package de.janlucaklees.kannji.datatypes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.janlucaklees.kannji.values.JsonKeys;

public class Kanji {

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
		_id = kanjiJson.getLong( JsonKeys.KANJI_ID );
		_literal = kanjiJson.getString( JsonKeys.LITERAL ).toCharArray()[ 0 ];
		_stroke_count = (byte) kanjiJson.getInt( JsonKeys.STROKE_COUNT );

		// extract readings
		extractReadings( kanjiJson );

		// extract meanings
		extractMeanings( kanjiJson );
	}

	private void extractReadings( JSONObject kanjiJson ) throws JSONException {
		_onReadings = new ArrayList<>();
		_kunReadings = new ArrayList<>();
		_nanoriReadings = new ArrayList<>();

		JSONObject readingsJson = kanjiJson.getJSONObject( JsonKeys.READINGS );

		Iterator<String> readingsIterator = readingsJson.keys();
		while ( readingsIterator.hasNext() ) {
			String key = readingsIterator.next();

			JSONObject readingJson = readingsJson.getJSONObject( key );

			switch ( readingJson.getString( JsonKeys.READINGS_TYPE ) ) {

				case JsonKeys.READINGS_TYPE_ONYOMI:
					_onReadings.add( readingJson.getString( JsonKeys.READINGS_READING ) );
					break;

				case JsonKeys.READINGS_TYPE_KUNYOMI:
					_kunReadings.add( readingJson.getString( JsonKeys.READINGS_READING ) );
					break;

				case JsonKeys.READINGS_TYPE_NANORI:
					_nanoriReadings.add( readingJson.getString( JsonKeys.READINGS_READING ) );
					break;
			}
		}
	}

	private void extractMeanings( JSONObject kanjiJson ) throws JSONException {
		_meanings = new HashMap<>();

		JSONObject meaningsJson = kanjiJson.getJSONObject( JsonKeys.MEANINGS );

		Iterator<String> meaningsIterator = meaningsJson.keys();
		while ( meaningsIterator.hasNext() ) {
			String key = meaningsIterator.next();

			JSONObject meaningJson = meaningsJson.getJSONObject( key );

			String meaning = meaningJson.getString( JsonKeys.MEANINGS_MEANING );
			String language = meaningJson.getString( JsonKeys.MEANINGS_LANGUAGE );

			Log.d( "insert_meaning", language + ", " + meaning );

			if ( !_meanings.containsKey( language ) ) {
				_meanings.put( language, new HashSet<String>() );
			}

			_meanings.get( language ).add( meaning );
		}

		Log.d( "g", _meanings.toString() );
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
