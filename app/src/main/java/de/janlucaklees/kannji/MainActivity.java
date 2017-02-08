package de.janlucaklees.kannji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.janlucaklees.kannji.Quizz.MultipleChoice;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user clicks the Start Quizz button
     */
    public void startQuiz(View view) {
        Intent intent = new Intent(this, MultipleChoice.class);
        startActivity(intent);
    }
}
