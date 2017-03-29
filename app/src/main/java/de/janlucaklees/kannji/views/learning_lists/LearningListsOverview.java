package de.janlucaklees.kannji.views.learning_lists;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.janlucaklees.kannji.R;
import de.janlucaklees.kannji.datatypes.learning_lists.LearningList;
import de.janlucaklees.kannji.datatypes.learning_lists.LearningListBrief;
import de.janlucaklees.kannji.services.database.ApiV2Connector;
import de.janlucaklees.kannji.services.database.ApiV2Interface;
import de.janlucaklees.kannji.views.DrawerActivity;

public class LearningListsOverview extends DrawerActivity implements LearningListBriefFragment.onLearningListSelectionListener {
	
	private SwipeRefreshLayout _refresherView;
	
	private List<Fragment> _addedListItems;
	
	private FragmentManager _listFragmentManager;
	
	private LearningListsOverview _this = this;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.content_learning_lists_overview );
		
		_addedListItems = new ArrayList<>();
		
		_listFragmentManager = getSupportFragmentManager();
		
		new LoadLearningsListsBrief().execute();
		
		_refresherView = (SwipeRefreshLayout) findViewById( R.id.activity_kanji_list_refresher );
		
		_refresherView.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				_refresherView.setRefreshing( true );
				new LoadLearningsListsBrief().execute();
			}
		} );
		
	}
	
	private void clearList() {
		FragmentTransaction t = _listFragmentManager.beginTransaction();
		
		for ( Fragment fragment : _addedListItems ) {
			t.remove( fragment );
		}
		
		t.commit();
		
		_addedListItems.clear();
	}
	
	@Override
	public void onLearningListSelection( long learningListId, String learningListName ) {
		Intent intent = new Intent( this, LearningListDetail.class );
		intent.putExtra( LearningList.ID, learningListId );
		intent.putExtra( LearningList.NAME, learningListName );
		startActivity( intent );
	}
	
	private class LoadLearningsListsBrief extends AsyncTask<String, Void, List<LearningListBrief>> {
		
		private Throwable _error;
		
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected List<LearningListBrief> doInBackground( String... args ) {
			ApiV2Interface apiConnector = new ApiV2Connector();
			
			// Getting JSON from URL
			List<LearningListBrief> learningListsBrief = null;
			try {
				learningListsBrief = apiConnector.getAllLearningListsBrief();
			} catch ( Exception e ) {
				_error = e;
				return null;
			}
			
			return learningListsBrief;
		}
		
		@Override
		protected void onPostExecute( List<LearningListBrief> LearningListsBrief ) {
			
			// if there was an error, display it stop.
			if ( _error != null ) {
				displayError();
				return;
			}
			
			clearList();
			
			FragmentTransaction t = _listFragmentManager.beginTransaction();
			
			for ( LearningListBrief learningListBrief : LearningListsBrief ) {
				
				LearningListBriefFragment leraningListBriefFragment = LearningListBriefFragment.newInstance( learningListBrief.getId(), learningListBrief.getName() );
				
				_addedListItems.add( leraningListBriefFragment );
				t.add( R.id.activity_kanji_list_content, leraningListBriefFragment );
			}
			
			t.commit();
			
			_refresherView.setRefreshing( false );
		}
		
		private void displayError() {
			_error.printStackTrace();
			
			clearList();
			
			TextView errorView = new TextView( _this );
			errorView.setText( "Error:" + _error.getMessage() + ". Cause:" + _error.getCause() );
			
			LinearLayout linearLayout = (LinearLayout) findViewById( R.id.activity_kanji_list_content );
			
			linearLayout.addView( errorView );
			
			_refresherView.setRefreshing( false );
		}
	}
}
