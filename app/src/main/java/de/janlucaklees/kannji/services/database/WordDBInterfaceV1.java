package de.janlucaklees.kannji.services.database;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import de.janlucaklees.kannji.datatypes.learning_entries.Kanji;
import de.janlucaklees.kannji.datatypes.learning_lists.LearningList;
import de.janlucaklees.kannji.datatypes.learning_lists.LearningListBrief;

/**
 * Created by Jan-Luca Klees on 2017-02-27.
 */
public interface WordDBInterfaceV1 {
	
	Kanji getKanji( long kanjiId ) throws IOException, JSONException;
	
	Kanji getRandomKanji() throws IOException, JSONException;
	
	LearningList getList( long id ) throws IOException, JSONException;
	
	List<LearningListBrief> getAllListsBrief() throws IOException, JSONException;
}
