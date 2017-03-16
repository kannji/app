package de.janlucaklees.kannji.datatypes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LearningList extends LearningListBrief {

	public static final String ID = "list_id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String THUMBNAIL_URL = "thumbnail_url";
	public static final String ENTRIES = "entries";
	public static final String ENTRIES_TYPE = "type";

	public static final String ENTRY_TYPE_KANJI = "kanji";

	private List<LearningEntry> _entries;

	public LearningList( JSONObject learningListJson ) throws JSONException {
		super( learningListJson );

		// extract entries
		extractEntries( learningListJson );
	}

	private void extractEntries( JSONObject learningListJson ) throws JSONException {
		JSONObject entriesJson = learningListJson.getJSONObject( ENTRIES );

		ArrayList<LearningEntry> entryList = new ArrayList<LearningEntry>();

		Iterator<String> jsonListEntryIterator = entriesJson.keys();

		while ( jsonListEntryIterator.hasNext() ) {
			String key = jsonListEntryIterator.next();
			JSONObject currentEntryJson = entriesJson.getJSONObject( key );

			LearningEntry currentEntry;

			String entryType = currentEntryJson.getString( ENTRIES_TYPE );
			switch ( entryType ) {
				case ENTRY_TYPE_KANJI:
					currentEntry = new Kanji( currentEntryJson );
					entryList.add( currentEntry );
					break;
			}
		}

		_entries = entryList;
	}

	public List<LearningEntry> getEntries() {
		return _entries;
	}
}
