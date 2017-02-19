package de.janlucaklees.kannji.List;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import de.janlucaklees.kannji.DB.WordDBAdministrationInterface;
import de.janlucaklees.kannji.R;

public class AddWord extends AppCompatActivity {

    private WordDBAdministrationInterface _wdbai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        _wdbai = new WordDBAdministrationInterface();
    }

    /**
     * Called when the user clicks the Start Quizz button
     */
    public void addWord(View view) {
        EditText wordText = (EditText) findViewById(R.id.word);
        EditText readingText = (EditText) findViewById(R.id.reading);
        EditText translationText = (EditText) findViewById(R.id.translation);

        Log.d("", translationText.getText().toString());

        _wdbai.storeWord(wordText.getText().toString(), readingText.getText().toString(), translationText.getText().toString());
    }
}
