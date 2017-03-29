package de.janlucaklees.kannji.services.database;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import de.janlucaklees.kannji.datatypes.learning_entries.Kanji;
import de.janlucaklees.kannji.datatypes.learning_entries.LearningEntry;
import de.janlucaklees.kannji.datatypes.learning_lists.LearningList;
import de.janlucaklees.kannji.datatypes.learning_lists.LearningListBrief;

public interface ApiV2Interface {
	
	/**
	 * Get's a random kanji from the specified learning list.
	 *
	 * @param learningListId The learning-list to get the random kanji from.
	 * @return One randomly chosen kanji from the learning-list
	 * @throws IOException
	 * @throws JSONException
	 */
	Kanji getRandomKanjiFromLearningList( long learningListId ) throws IOException, JSONException;
	
	/**
	 * Gets a list of brief information of learning-lists from the server.
	 * This is the default function that always gets the first page, like 'getAllListsBrief( 1 )'.
	 *
	 * @return a list of brief-learning-lists
	 * @throws IOException
	 * @throws JSONException
	 */
	List<LearningListBrief> getAllLearningListsBrief() throws IOException, JSONException;
	
	/**
	 * Gets the specified page of brief information of learning-lists from the server as a list.
	 *
	 * @param pageNumber the page number to be requested with the first page having the number 1.
	 * @return a list of all learning-lists brief information on the specified page
	 * @throws IOException
	 * @throws JSONException
	 */
	List<LearningListBrief> getAllLearningListsBrief( int pageNumber ) throws IOException, JSONException;
	
	/**
	 * This method gets all information for a learning-list, including the brief information and the first page of learning-entries.
	 *
	 * @param learningListId the id of the learning-list to be queried from the server
	 * @return The learning-list
	 * @throws IOException
	 * @throws JSONException
	 */
	LearningList getLearningListDetail( long learningListId ) throws IOException, JSONException;
	
	/**
	 * Get's a specified page of learning-entries from a specified learning-list.
	 *
	 * @param learningListId the learning-list to get the learning-entries from
	 * @param pageNumber     the page to be queried, first page being 1
	 * @return A list of the learning-entries of the specified list and page
	 * @throws IOException
	 * @throws JSONException
	 */
	List<LearningEntry> getLearningEntryPageFromLearningList( long learningListId, int pageNumber ) throws IOException, JSONException;
}
