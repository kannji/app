package de.janlucaklees.kannji.views.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.janlucaklees.kannji.MainActivity;
import de.janlucaklees.kannji.R;
import de.janlucaklees.kannji.datatypes.Kanji;
import de.janlucaklees.kannji.services.database.WordDBServerConnectorV1;
import de.janlucaklees.kannji.views.list.KanjiFragment;
import de.janlucaklees.kannji.views.list.KanjiList;

public class QuizYesNo extends AppCompatActivity {

	private WordDBServerConnectorV1 _wdbsc;

	private boolean _answerCorrect = false;

	private Button _yesButton;
	private Button _noButton;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_quiz_yes_no );

		new LoadKanjiTask().execute();
	}

	public void yesButtonClicked( View view ) {

		_yesButton = (Button) findViewById( R.id.activity_quiz_yes_no_button_yes );

		if( _answerCorrect ) {
			// congratz
			_yesButton.setBackgroundColor( Color.GREEN );
		} else {
			// fail
			_yesButton.setBackgroundColor( Color.RED );
		}

		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

	public void noButtonClicked( View view ) {

		_noButton = (Button) findViewById( R.id.activity_quiz_yes_no_button_no );

		if( !_answerCorrect ) {
			// congratz
			_noButton.setBackgroundColor( Color.GREEN );
		} else {
			// fail
			_noButton.setBackgroundColor( Color.RED );
		}

		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

	private class LoadKanjiTask extends AsyncTask<Void, Void, List<Kanji>> {

		private Throwable _error;

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected List<Kanji> doInBackground( Void... args ) {
			WordDBServerConnectorV1 kanjiLoader = new WordDBServerConnectorV1();

			// Getting JSON from URL
			List<Kanji> kanjiList = new ArrayList<Kanji>(  );

			try {
				kanjiList.add( kanjiLoader.getRandomKanji() );
				kanjiList.add( kanjiLoader.getRandomKanji() );
			} catch ( Exception e ) {
				_error = e;
				return null;
			}

			return kanjiList;
		}

		@Override
		protected void onPostExecute( List<Kanji> kanjis ) {

			// if there was an error, display it stop.
			if( _error != null ) {
				displayError();
				return;
			}

			// get question and answer
			Kanji questionKanji = kanjis.get( 0 );

			// get answer
			String answer;

			Random rnd = new Random();

			if( rnd.nextBoolean() ) {

				List<String> allReadings = new ArrayList<String>( questionKanji.getKunReadings() );
				allReadings.addAll( questionKanji.getOnReadings() );

				answer = allReadings.get( allReadings.size() % rnd.nextInt( ) - 1 );

			} else {
				Kanji givenAnswerKanji = kanjis.get( 1 );

				List<String> allReadings = new ArrayList<String>( givenAnswerKanji.getKunReadings() );
				allReadings.addAll( givenAnswerKanji.getOnReadings() );

				answer = allReadings.get( allReadings.size() % rnd.nextInt( ) - 1 );
			}

			// decide if answer correct
			if( questionKanji.getOnReadings().contains( answer ) || questionKanji.getKunReadings().contains( answer ) ) {
				_answerCorrect = true;
			}

			// display
			TextView questionView = (TextView) findViewById( R.id.activity_quiz_yes_no_question_field );
			questionView.setText( questionKanji.getLiteral().toString() );

			TextView answerView = (TextView) findViewById( R.id.activity_quiz_yes_no_given_answer );
			answerView.setText( answer );
		}

		private void displayError() {
			TextView questionView = (TextView) findViewById( R.id.activity_quiz_yes_no_question_field );
			questionView.setTextSize( 14 );
			questionView.setText( _error.getLocalizedMessage() );
		}
	}
}
