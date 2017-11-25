package ca.qc.cegep_heritage.mhaley_a03_hangman;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        try {
            String[] arrayString = WordLists.getEasyList();
            TextView txtWord = findViewById(R.id.txtTheWord);
            txtWord.setText(arrayString[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
