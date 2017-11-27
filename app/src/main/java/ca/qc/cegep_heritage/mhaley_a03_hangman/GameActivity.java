package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.LinkedList;

public class GameActivity extends AppCompatActivity {
    /* The actual game screen. All gameplay takes place inside this activity. */

    private EditText edtxtGuess;
    private TextView txtWord;
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

        // Hide the hangman
        hangmanHead.setVisibility(View.INVISIBLE);
        hangmanBody.setVisibility(View.INVISIBLE);
        hangmanLeftArm.setVisibility(View.INVISIBLE);
        hangmanLeftLeg.setVisibility(View.INVISIBLE);
        hangmanRightArm.setVisibility(View.INVISIBLE);
        hangmanRightLeg.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences(Options.OPTIONS, Context.MODE_PRIVATE);

        edtxtGuess = findViewById(R.id.edtxtGuess);
        txtWord = findViewById(R.id.txtTheWord);

        // Get a new word, and catch an IOException if there's an error reading the files
        try {
            word = WordFactory.getWord(sharedPreferences.getInt(Options.MIN_LENGTH, 3),
                    sharedPreferences.getInt(Options.MAX_LENGTH, 13),
                    sharedPreferences.getInt(Options.DIFFICULTY, 0) ==
                            OptionsActivity.getEasyRadioButtonId() ? 0 : 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder emptyWord = new StringBuilder();

        // Build the "placeholder" word
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

                // Take focus away from the input
                edtxtGuess.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                // Check for empty input
                if (guessedLetter.equals("")) {
                    test = Toast.makeText(GameActivity.this, "Enter a letter!", Toast.LENGTH_LONG);
                    test.show();
                    return;
                }

                // Check for duplicate input
                if (guessedLetters.contains(guessedLetter.toString().charAt(0))) {
                    test = Toast.makeText(GameActivity.this, "Duplicate letter!", Toast.LENGTH_LONG);
                    test.show();
                    return;
                }

                guessedLetters.add(guessedLetter.toString().charAt(0));

                if (word.isLetterInWord(guessedLetter)) {
                    // The following runs if the input char is inside the word string
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

                    // Win condition
                    if (correctLetters.size() == word.getLength()) {
                        correctLetters = null;
                        Intent winner = new Intent(GameActivity.this, WinActivity.class);
                        startActivity(winner);
                    }
                } else {
                    // The following runs if the input was not inside the word string
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
                }

                edtxtGuess.setText("");

                // Lose condition
                if (wrongAnswers == 6) {
                    correctLetters = null;
                    Intent gameOver = new Intent(GameActivity.this, LoseActivity.class);
                    gameOver.putExtra("actualWord", word.getWord());
                    startActivity(gameOver);
                }
            }
        });
    } // onCreate(...)

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("guess", edtxtGuess.getText().toString());
        savedInstanceState.putString("displayWord", txtWord.getText().toString());
        savedInstanceState.putParcelable("word", word);
        savedInstanceState.putSerializable("guessedLetters", guessedLetters);
        savedInstanceState.putSerializable("correctLetters", correctLetters);
        savedInstanceState.putInt("wrongAnswers", wrongAnswers);
    } // onSaveInstanceState(...)

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        edtxtGuess.setText(savedInstanceState.getString("guess"));
        word = savedInstanceState.getParcelable("word");
        txtWord.setText(savedInstanceState.getString("displayWord"));
        guessedLetters = (LinkedList<Character>) savedInstanceState.getSerializable("guessedLetters");
        correctLetters = (LinkedList<Pair>) savedInstanceState.getSerializable("correctLetters");

        wrongAnswers = savedInstanceState.getInt("wrongAnswers");

        TextView hangmanHead = findViewById(R.id.drawHead);
        TextView hangmanBody = findViewById(R.id.drawBody);
        TextView hangmanLeftArm = findViewById(R.id.drawLeftArm);
        TextView hangmanLeftLeg = findViewById(R.id.drawLeftLeg);
        TextView hangmanRightArm = findViewById(R.id.drawRightArm);

        switch (wrongAnswers) {
            case 5:
                hangmanLeftLeg.setVisibility(View.VISIBLE);
            case 4:
                hangmanRightArm.setVisibility(View.VISIBLE);
            case 3:
                hangmanLeftArm.setVisibility(View.VISIBLE);
            case 2:
                hangmanBody.setVisibility(View.VISIBLE);
            case 1:
                hangmanHead.setVisibility(View.VISIBLE);
            default:
                break;
        }
    } // onRestoreInstanceState

}
