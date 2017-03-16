package de.janlucaklees.kannji.views.list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.janlucaklees.kannji.R;
import de.janlucaklees.kannji.datatypes.LearningList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link onLearningListSelectionListener} interface
 * to handle interaction events.
 * Use the {@link LearningListBriefFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearningListBriefFragment extends Fragment {

	private long _id;
	private String _name;

	private onLearningListSelectionListener learningListSelectionListener;

	public LearningListBriefFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param name Parameter 1.
	 * @return A new instance of fragment LearningListBriefFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static LearningListBriefFragment newInstance( long id, String name ) {
		LearningListBriefFragment fragment = new LearningListBriefFragment();
		Bundle args = new Bundle();
		args.putLong( LearningList.ID, id );
		args.putString( LearningList.NAME, name );
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		if ( getArguments() != null ) {
			_id = getArguments().getLong( LearningList.ID );
			_name = getArguments().getString( LearningList.NAME );
		}
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate( R.layout.fragment_learning_list_brief, container, false );

		// insert stroke count
		TextView nameView = (TextView) rootView.findViewById( R.id.fragment_learning_list_brief_name );
		nameView.setText( _name );

		// add click listener
		View ev = rootView.findViewById( R.id.fragment_learning_list_brief );
		ev.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				if ( learningListSelectionListener != null ) {
					learningListSelectionListener.onLearningListSelection( _id, _name );
				}
			}
		} );

		return rootView;
	}

	@Override
	public void onAttach( Context context ) {
		super.onAttach( context );
		if ( context instanceof onLearningListSelectionListener ) {
			learningListSelectionListener = (onLearningListSelectionListener) context;
		} else {
			throw new RuntimeException( context.toString()
					+ " must implement onLearningListSelectionListener" );
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		learningListSelectionListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface onLearningListSelectionListener {
		void onLearningListSelection( long learningListId, String learningListName );
	}
}
