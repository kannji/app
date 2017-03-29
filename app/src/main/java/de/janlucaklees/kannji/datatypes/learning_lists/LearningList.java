package de.janlucaklees.kannji.datatypes.learning_lists;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.janlucaklees.kannji.datatypes.learning_entries.Kanji;
import de.janlucaklees.kannji.datatypes.learning_entries.LearningEntry;

public class LearningList extends LearningListBrief {
	
	public static final String ID = "list_id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String THUMBNAIL_URL = "thumbnail_url";
	public static final String LEARNING_ENTRIES_PAGE = "learning_entries_page";
	public static final String LEARNING_ENTRIES_PAGE__PAGE_NUMBER = "page_count";
	public static final String LEARNING_ENTRIES_PAGE__ENTRIES = "learning_entries";
	public static final String LEARNING_ENTRIES_PAGE__ENTRIES__ENTRIES_TYPE = "type";
	
	public static final String ENTRY_TYPE_KANJI = "kanji";
	
	// TODO make this able to handle paginated loading of entries from the server
	private List<LearningEntry> _entries;
	
	
	public static List<LearningEntry> getLearningEntriesFromLearningListJson( JSONObject learningListJson ) throws JSONException {
		ArrayList<LearningEntry> learningEntries = new ArrayList<>();
		JSONArray learningEntriesJsonArray = learningListJson.getJSONObject( LEARNING_ENTRIES_PAGE ).getJSONArray( LEARNING_ENTRIES_PAGE__ENTRIES );
		
		for ( int i = 0; i < learningEntriesJsonArray.length(); i++ ) {
			JSONObject currentEntryJson = learningEntriesJsonArray.getJSONObject( i );
			
			String type = currentEntryJson.getString( LEARNING_ENTRIES_PAGE__ENTRIES__ENTRIES_TYPE );
			
			switch ( type ) {
				case ENTRY_TYPE_KANJI:
					learningEntries.add( new Kanji( currentEntryJson ) );
					break;
			}
		}
		
		return learningEntries;
	}
	
	public LearningList( JSONObject learningListJson ) throws JSONException {
		super( learningListJson );
		
		// extract entries
		_entries = getLearningEntriesFromLearningListJson( learningListJson );
	}
	
	public List<LearningEntry> getEntries() {
		return _entries;
	}
}
