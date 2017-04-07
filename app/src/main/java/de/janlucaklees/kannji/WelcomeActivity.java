package de.janlucaklees.kannji;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.danlew.android.joda.JodaTimeAndroid;

import de.janlucaklees.kannji.views.DrawerActivity;
import de.janlucaklees.kannji.views.LoginActivity;
import de.janlucaklees.kannji.views.learning_lists.LearningListsOverview;
import de.janlucaklees.kannji.views.quizzes.QuizYesNo;

/**
 * The activity that welcomes the user when the app is started.
 * It extends the Drawer activity so that it has the default drawer.
 */
public class WelcomeActivity extends DrawerActivity {
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.content_welcome );
		JodaTimeAndroid.init( this );
	}
	
	/**
	 * Method called when user clicks on the Quiz button.
	 * It starts the yes-no quiz activity.
	 */
	public void startQuiz( View view ) {
		Intent intent = new Intent( this, QuizYesNo.class );
		startActivity( intent );
	}
	
	/**
	 * Method called when user clicks on the List button.
	 * It starts the learning list overview activity.
	 */
	public void viewLearningLists( View view ) {
		Intent intent = new Intent( this, LearningListsOverview.class );
		startActivity( intent );
	}
	
	/**
	 * Method called when user clicks on the List button.
	 * It starts the learning list overview activity.
	 */
	public void login( View view ) {
		Intent intent = new Intent( this, LoginActivity.class );
		startActivity( intent );
	}
}
