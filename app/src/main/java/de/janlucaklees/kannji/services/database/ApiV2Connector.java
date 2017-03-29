package de.janlucaklees.kannji.services.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.janlucaklees.kannji.datatypes.learning_entries.Kanji;
import de.janlucaklees.kannji.datatypes.learning_entries.LearningEntry;
import de.janlucaklees.kannji.datatypes.learning_lists.LearningList;
import de.janlucaklees.kannji.datatypes.learning_lists.LearningListBrief;
import de.janlucaklees.kannji.values.GeneralValues;

import static de.janlucaklees.kannji.datatypes.learning_lists.LearningList.getLearningEntriesFromLearningListJson;
import static de.janlucaklees.kannji.values.GeneralValues.DEFAULT_PAGE_SIZE;

public class ApiV2Connector implements ApiV2Interface {
	
	public static final String API_V2_URL = GeneralValues.API_URL + "/v2";
	
	@Override
	public Kanji getRandomKanjiFromLearningList( long learningListId ) throws IOException, JSONException {
		GetRequest request = new GetRequest( API_V2_URL + "/lists/" + learningListId + "/random" );
		request.setFilter( "type", "kanji" );
		
		JSONObject kanjiJson = request.getJSON();
		
		return new Kanji( kanjiJson );
	}
	
	@Override
	public List<LearningListBrief> getAllLearningListsBrief() throws IOException, JSONException {
		return getAllLearningListsBrief( 1 );
	}
	
	@Override
	public List<LearningListBrief> getAllLearningListsBrief( int pageNumber ) throws IOException, JSONException {
		// create request and add data
		GetRequest request = new GetRequest( API_V2_URL + "/lists/all/" );
		request.setPaging( pageNumber, DEFAULT_PAGE_SIZE );
		
		// execute request
		JSONObject learningListsJson = request.getJSON();
		
		// process data
		ArrayList<LearningListBrief> learningListsBrief = new ArrayList<>();
		JSONArray learningListJsonArray = learningListsJson.getJSONArray( "learning_lists" );
		
		for ( int i = 0; i < learningListJsonArray.length(); i++ ) {
			LearningListBrief learningListBrief = new LearningListBrief( learningListJsonArray.getJSONObject( i ) );
			learningListsBrief.add( learningListBrief );
		}
		
		return learningListsBrief;
	}
	
	@Override
	public LearningList getLearningListDetail( long learningListId ) throws IOException, JSONException {
		GetRequest request = new GetRequest( API_V2_URL + "/lists/" + learningListId + "/" );
		request.setPaging( 1, DEFAULT_PAGE_SIZE );
		
		JSONObject learningListJson = request.getJSON();
		
		return new LearningList( learningListJson );
	}
	
	@Override
	public List<LearningEntry> getLearningEntryPageFromLearningList( long learningListId, int pageNumber ) throws IOException, JSONException {
		// create request and add data
		GetRequest request = new GetRequest( API_V2_URL + "/lists/" + learningListId + "/" );
		request.setPaging( pageNumber, DEFAULT_PAGE_SIZE );
		
		// execute request
		JSONObject learningListJson = request.getJSON();
		
		return getLearningEntriesFromLearningListJson( learningListJson );
	}
}
