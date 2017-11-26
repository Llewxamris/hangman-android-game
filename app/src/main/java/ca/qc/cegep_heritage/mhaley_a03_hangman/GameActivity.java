package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.LinkedList;

public class GameActivity extends AppCompatActivity {

    private EditText edtxtGuess;
    private Word word;
    private LinkedList<Character> guessedLetters = new LinkedList<>();
    private LinkedList<Pair> correctLetters = new LinkedList<>();
    private int wrongAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final TextView hangmanHead = findViewById(R.id.drawHead);
        final TextView hangmanBody = findViewById(R.id.drawBody);
        final TextView hangmanLeftArm = findViewById(R.id.drawLeftArm);
        final TextView hangmanLeftLeg = findViewById(R.id.drawLeftLeg);
        final TextView hangmanRightArm = findViewById(R.id.drawRightArm);
        final TextView hangmanRightLeg = findViewById(R.id.drawRightLeg);

        hangmanHead.setVisibility(View.INVISIBLE);
        hangmanBody.setVisibility(View.INVISIBLE);
        hangmanLeftArm.setVisibility(View.INVISIBLE);
        hangmanLeftLeg.setVisibility(View.INVISIBLE);
        hangmanRightArm.setVisibility(View.INVISIBLE);
        hangmanRightLeg.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("options", Context.MODE_PRIVATE);

        edtxtGuess = findViewById(R.id.edtxtGuess);

        try {
            word = WordFactory.getWord(sharedPreferences.getInt("minLength", 3),
                    sharedPreferences.getInt("maxLength", 13),
                    sharedPreferences.getInt("difficulty", 0) == R.id.rdoEasy ? 0 : 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final TextView txtWord = findViewById(R.id.txtTheWord);
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

                if (guessedLetters.contains(guessedLetter.toString().charAt(0))) {
                    test = Toast.makeText(GameActivity.this, "Duplicate letter!", Toast.LENGTH_LONG);
                    test.show();
                    return;
                }

                guessedLetters.add(guessedLetter.toString().charAt(0));

                if (word.isLetterInWord(guessedLetter)) {
                    test = Toast.makeText(GameActivity.this, "You guessed a letter!", Toast.LENGTH_LONG);
                    Integer[] indexes = word.getLocationsOfLetter(guessedLetter.charAt(0));

                    for (int index : indexes) {
                        Pair<Integer, Character> letterLocation = new Pair<>(index, guessedLetter.charAt(0));
                        correctLetters.add(letterLocation);
                    }

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < word.getLength(); i++) {
                        boolean isFound = false;

                        for (Pair letterLocal : correctLetters) {
                            if ((Integer) letterLocal.first == i) {
                                sb.append(letterLocal.second);
                                sb.append(" ");
                                isFound = true;
                            }
                        }

                        if (!isFound) {
                            sb.append("_ ");
                        }
                    }

                    txtWord.setText(sb.toString());
                    test.show();
                } else {
                    wrongAnswers++;

                    switch (wrongAnswers) {
                        case 1:
                            hangmanHead.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            hangmanBody.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            hangmanLeftArm.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            hangmanRightArm.setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            hangmanLeftLeg.setVisibility(View.VISIBLE);
                            break;
                        case 6:
                            hangmanRightLeg.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }

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
