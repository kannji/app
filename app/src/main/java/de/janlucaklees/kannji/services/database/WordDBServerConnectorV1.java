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
import de.janlucaklees.kannji.datatypes.LearningList;
import de.janlucaklees.kannji.datatypes.LearningListBrief;
import de.janlucaklees.kannji.values.GeneralValues;

public class WordDBServerConnectorV1 implements WordDBInterfaceV1 {
	
	private static final Random _rnd = new Random();
	
	@Override
	public Kanji getKanji( long kanjiId ) throws IOException, JSONException {
		JSONObject jsonKanji = getJsonFromServer( "kanji/" + kanjiId );
		
		return new Kanji( jsonKanji );
	}
	
	@Override
	public Kanji getRandomKanji() throws IOException, JSONException {
		JSONObject jsonKanji = getJsonFromServer( "kanji/random/" );
		
		return new Kanji( jsonKanji );
	}
	
	@Override
	public LearningList getList( long id ) throws IOException, JSONException {
		JSONObject jsonList = getJsonFromServer( "lists/" + id );
		
		return new LearningList( jsonList );
	}
	
	@Override
	public List<LearningListBrief> getAllListsBrief() throws IOException, JSONException {
		// TODO not really all lists, just the top ten or so; pagination would be useful
		JSONObject learningListsJson = getJsonFromServer( "lists/all/" );
		
		ArrayList<LearningListBrief> learningListsBrief = new ArrayList<>();
		Iterator<String> jsonIterator = learningListsJson.keys();
		while ( jsonIterator.hasNext() ) {
			String key = jsonIterator.next();
			
			LearningListBrief learningListBrief = new LearningListBrief( learningListsJson.getJSONObject( key ) );
			
			learningListsBrief.add( learningListBrief );
		}
		
		return learningListsBrief;
	}
	
	private JSONObject getJsonFromServer( String path ) throws IOException, JSONException {
		
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
		
		return new JSONObject( result.toString() );
	}
}
