package de.janlucaklees.kannji;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.janlucaklees.kannji.views.list.LearningListsOverview;
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
	
	public void startQuiz( View view ) {
		Intent intent = new Intent( this, QuizYesNo.class );
		startActivity( intent );
	}
	
	public void viewWords( View view ) {
		Intent intent = new Intent( this, LearningListsOverview.class );
		startActivity( intent );
	}
}
