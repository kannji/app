package de.janlucaklees.kannji.views.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Set;

import de.janlucaklees.kannji.R;
import de.janlucaklees.kannji.datatypes.Kanji;

public class KanjiFragment extends Fragment {

	private Kanji _kanji;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {

		// creawting the root layout
		View rootView = inflater.inflate( R.layout.fragment_kanji, container, false );

		// insert stroke count
		int strokeCount = _kanji.getStrokeCount();
		String strokeCountText = Integer.toString( strokeCount );
		TextView translationView = (TextView) rootView.findViewById( R.id.fragment_kanji_stroke_count );
		translationView.setText( strokeCountText );

		// insert literal
		char literal = _kanji.getLiteral();
		String literalText = Character.toString( literal );
		TextView literalView = (TextView) rootView.findViewById( R.id.fragment_kanji_literal );
		literalView.setText( literalText );

		// insert readings and meanings
		FragmentTransaction t = getChildFragmentManager().beginTransaction();

		// insert on-readings
		for ( String onReading : _kanji.getOnReadings() ) {

			KanjiReadingFragment kanjiReadingFragment = new KanjiReadingFragment();

			Bundle readingBundle = new Bundle();
			readingBundle.putString( Kanji.READINGS_READING, onReading );
			readingBundle.putString( Kanji.READINGS_TYPE, Kanji.READINGS_TYPE_ONYOMI );

			kanjiReadingFragment.setArguments( readingBundle );

			t.add( R.id.fragment_kanji_readings, kanjiReadingFragment );
		}

		// insert kun-readings
		for ( String kunReading : _kanji.getKunReadings() ) {

			KanjiReadingFragment kanjiReadingFragment = new KanjiReadingFragment();

			Bundle readingBundle = new Bundle();
			readingBundle.putString( Kanji.READINGS_READING, kunReading );
			readingBundle.putString( Kanji.READINGS_TYPE, Kanji.READINGS_TYPE_KUNYOMI );

			kanjiReadingFragment.setArguments( readingBundle );

			t.add( R.id.fragment_kanji_readings, kanjiReadingFragment );
		}

		// insert meanings
		Set<String> meanings = _kanji.getMeanings().get( "en-GB" );

		if ( meanings != null ) {
			for ( String meaning : meanings ) {

				KanjiMeaningFragment kanjiMeaningFragment = new KanjiMeaningFragment();

				Bundle readingBundle = new Bundle();
				readingBundle.putString( Kanji.MEANINGS_MEANING, meaning );

				kanjiMeaningFragment.setArguments( readingBundle );

				t.add( R.id.fragment_kanji_meanings, kanjiMeaningFragment );
			}
		}

		t.commit();

		// Inflate the layout for this fragment
		return rootView;
	}

	public void setKanji( Kanji kanji ) {
		this._kanji = kanji;
	}
}