package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        try {
            word = WordFactory.getWord(sharedPreferences.getInt("minLength", 3),
                    sharedPreferences.getInt("maxLength", 13),
                    sharedPreferences.getInt("difficulty", 0) == R.id.rdoEasy ? 0 : 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView txtWord = findViewById(R.id.txtTheWord);
        txtWord.setText(word.getWord());

        edtxtGuess = findViewById(R.id.edtxtGuess);
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
