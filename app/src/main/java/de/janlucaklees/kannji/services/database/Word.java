package de.janlucaklees.kannji.services.database;

import java.util.List;

public class Word {

    private long _id;
    private String _word;
    private List<String> _readings;
    private List<String> _translations;

    Word(long id, String word, List<String> readings, List<String> translations) {
        _id = id;
        _word = word;
        _readings = readings;
        _translations = translations;
    }

    public long getId() {
        return _id;
    }

    public String getWord() {
        return _word;
    }

    public List<String> getReadings() {
        return _readings;
    }

    public List<String> getTranslations() {
        return _translations;
    }
}