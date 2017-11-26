package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

public class GameActivity extends AppCompatActivity {

    private EditText edtxtGuess;
    private Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SharedPreferences sharedPreferences = getSharedPreferences("options", Context.MODE_PRIVATE);
        edtxtGuess = findViewById(R.id.edtxtGuess);

        try {
            word = WordFactory.getWord(sharedPreferences.getInt("minLength", 3),
                    sharedPreferences.getInt("maxLength", 13),
                    sharedPreferences.getInt("difficulty", 0) == R.id.rdoEasy ? 0 : 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView txtWord = findViewById(R.id.txtTheWord);
        StringBuilder emptyWord = new StringBuilder();

        for (int i = 0; i < word.getLength(); i++) {
            emptyWord.append("_");
            emptyWord.append(" ");
        }

        txtWord.setText(emptyWord.toString());

        Button btnGuess = findViewById(R.id.btnGuess);
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast test;
                CharSequence guessedLetter = edtxtGuess.getText().toString().toLowerCase();

                if (word.isLetterInWord(guessedLetter)) {
                    test = Toast.makeText(GameActivity.this, "You guessed a letter!", Toast.LENGTH_LONG);
                    test.show();
                } else {
                    test = Toast.makeText(GameActivity.this, "You didn't guess a letter!", Toast.LENGTH_LONG);
                    test.show();
                }
            }
        });
    }

    public void checkLetter(View v) {
        Button btn = findViewById(v.getId());
        btn.setClickable(false);
        btn.setEnabled(false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("guess", edtxtGuess.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        edtxtGuess.setText(savedInstanceState.getString("guess"));
    }

}
