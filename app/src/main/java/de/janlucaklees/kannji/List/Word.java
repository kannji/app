package de.janlucaklees.kannji.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.janlucaklees.kannji.R;

public class Word extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_word, container, false);

        String word = getArguments().getString(WordList.BUNDLE_WORD_KEY);

        TextView wordView = (TextView) rootView.findViewById(R.id.word);
        wordView.setTextSize(40);
        wordView.setText(word);

        String reading = getArguments().getString(WordList.BUNDLE_READING_KEY);

        TextView readingView = (TextView) rootView.findViewById(R.id.reading);
        readingView.setTextSize(20);
        readingView.setText(reading);

        String translation = getArguments().getString(WordList.BUNDLE_TRANSLATION_KEY);

        TextView translationView = (TextView) rootView.findViewById(R.id.translation);
        translationView.setTextSize(20);
        translationView.setText(translation);

        // Inflate the layout for this fragment
        return rootView;
    }
}