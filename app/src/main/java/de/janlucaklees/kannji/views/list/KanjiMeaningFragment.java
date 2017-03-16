package de.janlucaklees.kannji.views.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.janlucaklees.kannji.R;
import de.janlucaklees.kannji.datatypes.Kanji;

public class KanjiMeaningFragment extends Fragment {

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {

		// creawting the root layout
		View rootView = inflater.inflate( R.layout.fragment_kanji_meaning, container, false );

		// insert meaning
		String meaningText = getArguments().getString( Kanji.MEANINGS_MEANING );
		TextView meaningView = (TextView) rootView.findViewById( R.id.fragment_kanji_meaning_meaning );
		meaningView.setText( meaningText );

		// Inflate the layout for this fragment
		return rootView;
	}

}
