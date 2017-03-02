package de.janlucaklees.kannji.services.database;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import de.janlucaklees.kannji.datatypes.Kanji;
import de.janlucaklees.kannji.values.GeneralValues;

public class WordDBServerConnectorV1 implements WordDBInterfaceV1 {

	private static final Random _rnd = new Random();

	@Override
	public void storeWord( String word, String reading, String translation ) {

	}

	@Override
	public void storeWord( String word, List<String> readings, List<String> translations ) {

	}

	@Override
	public List<Word> getAllWords() {
		return null;
	}

	public List<Kanji> getAllKanji() {

		String result = null;

		try {
			result = getJsonFromServer( "kanji/" );
		} catch ( IOException e ) {
			e.printStackTrace();
		}

		JSONObject jObj = null;
		try {
			jObj = new JSONObject( result );
		} catch ( JSONException e ) {
			e.printStackTrace();
		}

		ArrayList<Kanji> kanjiList = new ArrayList<>();
		Iterator<String> jsonIterator = jObj.keys();
		while ( jsonIterator.hasNext() ) {
			String key = jsonIterator.next();

			JSONObject jsonKanji = null;
			Kanji kanji = null;
			try {
				jsonKanji = jObj.getJSONObject( key );
				kanji = new Kanji( jsonKanji );
				kanjiList.add( kanji );
			} catch ( JSONException e ) {
				e.printStackTrace();
			}

		}

		return kanjiList;
	}

	public Kanji getKanji( long kanjiId ) {
		String result = null;

		try {
			result = getJsonFromServer( "kanji/" + kanjiId );
		} catch ( IOException e ) {
			e.printStackTrace();
		}

		JSONObject jsonKanji = null;
		try {
			jsonKanji = new JSONObject( result );
		} catch ( JSONException e ) {
			e.printStackTrace();
		}

		Kanji kanji = null;
		try {
			kanji = new Kanji( jsonKanji );
		} catch ( JSONException e ) {
			e.printStackTrace();
		}

		return kanji;
	}

	public Kanji getRandomKanji( ) {
		return getKanji( _rnd.nextInt( 5 ) + 1 );
	}

	private String getJsonFromServer( String path ) throws IOException {

		// try creating the url
		URL url = new URL( GeneralValues.API_URL + path );

		// make url connection
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

		// getting input stream
		InputStream inputStream = new BufferedInputStream( urlConnection.getInputStream() );


		// read stream
		BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
		StringBuilder result = new StringBuilder();
		String line;
		while ( ( line = reader.readLine() ) != null ) {
			result.append( line ).append( '\n' );
		}

		// disconnect
		// TODO might be null
		urlConnection.disconnect();

		return result.toString();
	}
}
