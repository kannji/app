package de.janlucaklees.kannji.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import de.janlucaklees.kannji.DB.WordDBAdministrationInterface;
import de.janlucaklees.kannji.R;

public class WordList extends AppCompatActivity {

    public static final String BUNDLE_WORD_KEY = "word";
    public static final String BUNDLE_READING_KEY = "reading";
    public static final String BUNDLE_TRANSLATION_KEY = "translation";

    private WordDBAdministrationInterface _wdbai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        _wdbai = new WordDBAdministrationInterface();

        List<de.janlucaklees.kannji.DB.Word> words = _wdbai.getAllWords();

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();

        for (de.janlucaklees.kannji.DB.Word word : words) {

            Word fragment = new Word();

            Bundle bundle = new Bundle();
            bundle.putString(BUNDLE_WORD_KEY, word.getWord());

            List<String> readings = word.getReadings();
            if (!readings.isEmpty()) {
                bundle.putString(BUNDLE_READING_KEY, readings.get(0));
            }

            List<String> translations = word.getTranslations();
            if (!translations.isEmpty()) {
                bundle.putString(BUNDLE_TRANSLATION_KEY, translations.get(0));
            }

            fragment.setArguments(bundle);

            t.add(R.id.activity_word_list_content, fragment);
        }

        t.commit();
    }
}
