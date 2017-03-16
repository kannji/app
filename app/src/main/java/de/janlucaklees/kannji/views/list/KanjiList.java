package de.janlucaklees.kannji.views.list;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.janlucaklees.kannji.R;
import de.janlucaklees.kannji.datatypes.Kanji;
import de.janlucaklees.kannji.services.database.WordDBServerConnectorV1;

public class KanjiList extends AppCompatActivity {

	private WordDBServerConnectorV1 _wdbsc;
	private SwipeRefreshLayout _refresherView;

	private List<Fragment> _addedListItems;

	private FragmentManager _listFragmentManager;

	private KanjiList _this = this;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_kanji_list );

		_addedListItems = new ArrayList<>();

		_listFragmentManager = getSupportFragmentManager();

		new LoadKanjiTask().execute();

		_refresherView = (SwipeRefreshLayout) findViewById( R.id.activity_kanji_list_refresher );

		_refresherView.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new LoadKanjiTask().execute();
				_refresherView.setRefreshing( true );
				new LoadKanjiTask().execute();
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

	private class LoadKanjiTask extends AsyncTask<String, Void, List<Kanji>> {

		private Throwable _error;

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected List<Kanji> doInBackground( String... args ) {
			WordDBServerConnectorV1 kanjiLoader = new WordDBServerConnectorV1();

			// Getting JSON from URL
			List<Kanji> kanjiList = null;
			try {
				kanjiList = kanjiLoader.getAllKanji();
			} catch ( Exception e ) {
				_error = e;
				return null;
			}

			return kanjiList;
		}

		@Override
		protected void onPostExecute( List<Kanji> kanjis ) {

			// if there was an error, display it stop.
			if ( _error != null ) {
				displayError();
				return;
			}

			clearList();

			FragmentTransaction t = _listFragmentManager.beginTransaction();

			for ( Kanji kanji : kanjis ) {

				KanjiFragment kanjiFragment = new KanjiFragment();
				kanjiFragment.setKanji( kanji );

				_addedListItems.add( kanjiFragment );
				t.add( R.id.activity_kanji_list_content, kanjiFragment );
			}

			t.commit();

			_refresherView.setRefreshing( false );
		}

		private void displayError() {
			clearList();

			TextView errorView = new TextView( _this );
			errorView.setText( _error.getLocalizedMessage() );

			LinearLayout linearLayout = (LinearLayout) findViewById( R.id.activity_kanji_list_content );

			linearLayout.addView( errorView );

			_refresherView.setRefreshing( false );
		}
	}
}
