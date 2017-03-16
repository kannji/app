package de.janlucaklees.kannji.views.list;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.janlucaklees.kannji.R;
import de.janlucaklees.kannji.datatypes.Kanji;

public class KanjiReadingFragment extends Fragment {

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {

		// creawting the root layout
		View rootView = inflater.inflate( R.layout.fragment_kanji_reading, container, false );

		// insert reading
		String readingText = getArguments().getString( Kanji.READINGS_READING );
		TextView readingView = (TextView) rootView.findViewById( R.id.fragment_kanji_reading_reading );
		readingView.setText( readingText );
		readingView.setTypeface( null, Typeface.BOLD_ITALIC );

		// setting abckground color when reading is onyomi
		String type = getArguments().getString( Kanji.READINGS_TYPE );
		if ( Kanji.READINGS_TYPE_ONYOMI.equals( type ) ) {
			rootView.setBackgroundColor( Color.parseColor( "#CCCCCC" ) );
		} else {
			rootView.setBackgroundColor( Color.parseColor( "#FFBBCC" ) );
		}

		// Inflate the layout for this fragment
		return rootView;
	}

}
