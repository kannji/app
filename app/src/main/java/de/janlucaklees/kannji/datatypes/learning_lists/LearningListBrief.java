package de.janlucaklees.kannji.datatypes.learning_lists;

import org.json.JSONException;
import org.json.JSONObject;

public class LearningListBrief {
	
	private long _id;
	private String _name;
	private String _description;
	private String _thumbnail_url;
	
	public LearningListBrief( JSONObject learningListJson ) throws JSONException {
		// extract basic information
		_id = learningListJson.getLong( LearningList.ID );
		_name = learningListJson.getString( LearningList.NAME );
		_description = learningListJson.getString( LearningList.DESCRIPTION );
		_thumbnail_url = learningListJson.getString( LearningList.THUMBNAIL_URL );
	}
	
	public long getId() {
		return _id;
	}
	
	public String getName() {
		return _name;
	}
	
	public String getDescription() {
		return _description;
	}
	
	public String getThumbnailUrl() {
		return _thumbnail_url;
	}
}
