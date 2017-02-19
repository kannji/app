package de.janlucaklees.kannji;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import de.janlucaklees.kannji.List.AddWord;
import de.janlucaklees.kannji.List.WordList;
import de.janlucaklees.kannji.Quizz.MultipleChoice;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private static Context _context;

    public static Context getAppContext() {
        return _context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = getApplicationContext();
    }

    /**
     * Called when the user clicks the Start Quizz button
     */
    public void startQuiz(View view) {
        Intent intent = new Intent(this, MultipleChoice.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void startAddingWords(View view) {
        Intent intent = new Intent(this, AddWord.class);
        startActivity(intent);
    }

    public void viewWords(View view) {
        Intent intent = new Intent(this, WordList.class);
        startActivity(intent);
    }
}
