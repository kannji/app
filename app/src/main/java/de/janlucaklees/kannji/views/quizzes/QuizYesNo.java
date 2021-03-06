package de.janlucaklees.kannji.views.quizzes;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.janlucaklees.kannji.R;
import de.janlucaklees.kannji.datatypes.learning_entries.Kanji;
import de.janlucaklees.kannji.services.database.ApiV2Connector;
import de.janlucaklees.kannji.services.database.ApiV2Interface;
import de.janlucaklees.kannji.views.DrawerActivity;

public class QuizYesNo extends DrawerActivity {
	
	public static final String BUNDLE_KEY_COMBO = "combo_count";
	
	private boolean _answerCorrect = false;
	
	private Button _yesButton;
	private Button _noButton;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.content_quiz_yes_no );
		
		Intent intent = getIntent();
		String comboCount = Integer.toString( intent.getIntExtra( BUNDLE_KEY_COMBO, 0 ) );
		TextView comboView = (TextView) findViewById( R.id.activity_quiz_yes_no_combo );
		comboView.setText( comboCount );
		
		_yesButton = (Button) findViewById( R.id.activity_quiz_yes_no_button_yes );
		_noButton = (Button) findViewById( R.id.activity_quiz_yes_no_button_no );
		
		new LoadKanjiTask().execute();
	}
	
	public void yesButtonClicked( View view ) {
		
		Intent intent = getIntent();
		
		if ( _answerCorrect ) {
			// congratz
			_yesButton.setBackgroundColor( Color.GREEN );
			intent.putExtra( BUNDLE_KEY_COMBO, intent.getIntExtra( BUNDLE_KEY_COMBO, 0 ) + 1 );
		} else {
			// fail
			_yesButton.setBackgroundColor( Color.RED );
			intent.putExtra( BUNDLE_KEY_COMBO, 0 );
		}
		
		finish();
		startActivity( intent );
	}
	
	public void noButtonClicked( View view ) {
		
		Intent intent = getIntent();
		
		if ( !_answerCorrect ) {
			// congratz
			_noButton.setBackgroundColor( Color.GREEN );
			intent.putExtra( BUNDLE_KEY_COMBO, intent.getIntExtra( BUNDLE_KEY_COMBO, 0 ) + 1 );
		} else {
			// fail
			_noButton.setBackgroundColor( Color.RED );
			intent.putExtra( BUNDLE_KEY_COMBO, 0 );
		}
		
		finish();
		startActivity( intent );
	}
	
	private class LoadKanjiTask extends AsyncTask<Void, Void, List<Kanji>> {
		
		private Throwable _error;
		
		@Override
		protected void onPreExecute() {
			_noButton.setEnabled( false );
			_yesButton.setEnabled( false );
		}
		
		@Override
		protected List<Kanji> doInBackground( Void... args ) {
			ApiV2Interface apiConnector = new ApiV2Connector();
			
			// Getting JSON from URL
			List<Kanji> kanjiList = new ArrayList<Kanji>();
			
			try {
				// TODO make selection what list to learn
				kanjiList.add( apiConnector.getRandomKanjiFromLearningList( 3 ) );
				kanjiList.add( apiConnector.getRandomKanjiFromLearningList( 3 ) );
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
			
			// get question and answer
			Kanji questionKanji = kanjis.get( 0 );
			
			// get answer
			String answer;
			
			Random rnd = new Random();
			
			if ( rnd.nextBoolean() ) {
				
				List<String> allQuestionReadings = new ArrayList<String>( questionKanji.getKunReadings() );
				allQuestionReadings.addAll( questionKanji.getOnReadings() );
				
				answer = allQuestionReadings.get( rnd.nextInt( allQuestionReadings.size() ) );
				
			} else {
				Kanji givenAnswerKanji = kanjis.get( 1 );
				
				List<String> allGivenReadings = new ArrayList<String>( givenAnswerKanji.getKunReadings() );
				allGivenReadings.addAll( givenAnswerKanji.getOnReadings() );
				
				answer = allGivenReadings.get( rnd.nextInt( allGivenReadings.size() ) );
			}
			
			// decide if answer correct
			if ( questionKanji.getOnReadings().contains( answer ) || questionKanji.getKunReadings().contains( answer ) ) {
				_answerCorrect = true;
			}
			
			// display
			TextView questionView = (TextView) findViewById( R.id.activity_quiz_yes_no_question_field );
			questionView.setText( questionKanji.getLiteral().toString() );
			
			TextView answerView = (TextView) findViewById( R.id.activity_quiz_yes_no_given_answer );
			answerView.setText( answer );
			
			_noButton.setEnabled( true );
			_yesButton.setEnabled( true );
		}
		
		private void displayError() {
			_error.printStackTrace();
			
			TextView questionView = (TextView) findViewById( R.id.activity_quiz_yes_no_question_field );
			questionView.setTextSize( 14 );
			questionView.setText( _error.getLocalizedMessage() );
		}
	}
}
