package de.janlucaklees.kannji;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import de.janlucaklees.kannji.views.list.AddWord;
import de.janlucaklees.kannji.views.list.KanjiList;
import de.janlucaklees.kannji.views.quiz.MultipleChoice;
import de.janlucaklees.kannji.views.quiz.QuizYesNo;


public class MainActivity extends AppCompatActivity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	private Context _context;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		_context = getApplicationContext();
	}

	/**
	 * Called when the user clicks the Start Quizz button
	 */
	public void startQuiz( View view ) {
		Intent intent = new Intent( this, QuizYesNo.class );
		startActivity( intent );
	}

	public void startAddingWords( View view ) {
		Intent intent = new Intent( this, AddWord.class );
		startActivity( intent );
	}

	public void viewWords( View view ) {
		Intent intent = new Intent( this, KanjiList.class );
		startActivity( intent );
	}
}
