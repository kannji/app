package de.janlucaklees.kannji.views.learning_lists;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.janlucaklees.kannji.R;
import de.janlucaklees.kannji.datatypes.learning_entries.Kanji;
import de.janlucaklees.kannji.datatypes.learning_entries.LearningEntry;
import de.janlucaklees.kannji.datatypes.learning_lists.LearningList;
import de.janlucaklees.kannji.services.database.WordDBServerConnectorV1;
import de.janlucaklees.kannji.views.DrawerActivity;
import de.janlucaklees.kannji.views.learning_entries.kanji.KanjiFragment;

public class LearningListDetail extends DrawerActivity {
	
	private List<Fragment> _addedListItems;
	
	private FragmentManager _listFragmentManager;
	
	private LearningListDetail _this = this;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_learning_list_detail );
		
		// set toolbar
		Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
		toolbar.setTitle( getIntent().getStringExtra( LearningList.NAME ) );
		setSupportActionBar( toolbar );
		
		FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
		fab.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
						.setAction( "Action", null ).show();
			}
		} );
		
		_addedListItems = new ArrayList<>();
		
		_listFragmentManager = getSupportFragmentManager();
		
		long learningListId = getIntent().getLongExtra( LearningList.ID, -1 );
		
		new LearningListDetail.LoadLearningListDetailTask().execute( learningListId );
		
	}
	
	private void clearList() {
		FragmentTransaction t = _listFragmentManager.beginTransaction();
		
		for ( Fragment fragment : _addedListItems ) {
			t.remove( fragment );
		}
		
		t.commit();
		
		_addedListItems.clear();
	}
	
	private class LoadLearningListDetailTask extends AsyncTask<Long, Void, LearningList> {
		
		private Throwable _error;
		
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected LearningList doInBackground( Long... args ) {
			WordDBServerConnectorV1 serverConnector = new WordDBServerConnectorV1();
			
			// Getting JSON from URL
			LearningList learningList = null;
			try {
				learningList = serverConnector.getList( args[ 0 ] );
			} catch ( Exception e ) {
				_error = e;
				return null;
			}
			
			return learningList;
		}
		
		@Override
		protected void onPostExecute( LearningList learningList ) {
			
			// if there was an error, display it stop.
			if ( _error != null ) {
				displayError();
				return;
			}
			
			clearList();
			
			// set name
			getSupportActionBar().setTitle( learningList.getName() );
			
			// set kanji
			FragmentTransaction t = _listFragmentManager.beginTransaction();
			
			for ( LearningEntry entry : learningList.getEntries() ) {
				
				if ( entry instanceof Kanji ) {
					Kanji kanji = (Kanji) entry;
					KanjiFragment kanjiFragment = new KanjiFragment();
					kanjiFragment.setKanji( kanji );
					
					_addedListItems.add( kanjiFragment );
					t.add( R.id.content_learning_list_detail_inner, kanjiFragment );
				}
			}
			
			t.commit();
		}
		
		private void displayError() {
			clearList();
			
			TextView errorView = new TextView( _this );
			errorView.setText( _error.getLocalizedMessage() );
			
			LinearLayout linearLayout = (LinearLayout) findViewById( R.id.activity_kanji_list_content );
			
			linearLayout.addView( errorView );
		}
	}
}
