package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class OptionsActivity extends AppCompatActivity {
    /* The options menu. Allows the user to change the game to their preferences, then saves them
    when the save button is pressed. */

    private EditText edtxtMinLength;
    private EditText edtxtMaxLength;
    private RadioGroup rdoGrpDifficulty;
    private Intent mainIntent;
    private static final int MIN_WORD_LENGTH = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        edtxtMinLength = findViewById(R.id.edtxtMinLength);
        edtxtMaxLength = findViewById(R.id.edtxtMaxLength);
        rdoGrpDifficulty = findViewById(R.id.rdoGrpDifficulty);

        mainIntent = new Intent(OptionsActivity.this, MainActivity.class);

        getSharedPreferences(); // Grab shared preferences. Set the widgets, and views to match

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder warningBuilder = new AlertDialog.Builder(OptionsActivity.this);
                warningBuilder.setTitle("Error");

                // Catch empty minimum length
                if (edtxtMinLength.getText().toString().equals("")) {
                    warningBuilder.setMessage(R.string.error_required_minLength);
                    warningBuilder.show();
                    return;
                }

                // Catch empty maximum length
                if (edtxtMaxLength.getText().toString().equals("")) {
                    warningBuilder.setMessage(R.string.error_required_maxLength);
                    warningBuilder.show();
                    return;
                }

                // Min/max is not empty if the code has reached this point
                int minLength = Integer.parseInt(edtxtMinLength.getText().toString());
                int maxLength = Integer.parseInt(edtxtMaxLength.getText().toString());

                // Catch size errors, if none: save the options
                if (minLength < MIN_WORD_LENGTH) {
                    // Catch min is less than 3
                    warningBuilder.setMessage(R.string.error_range_minLength);
                    warningBuilder.show();
                } else if (maxLength < minLength) {
                    // Catch max is less than min
                    warningBuilder.setMessage(R.string.error_range_maxLength);
                    warningBuilder.show();
                } else {
                    // Save the preferences
                    Snackbar saveSnackbar = Snackbar
                            .make(findViewById(R.id.layOptions), R.string.options_success,
                                    Snackbar.LENGTH_LONG);
                    saveSnackbar.show();
                    setSharedPreferences(); // Get the values, set them to the shared preferences
                }
            }
        });

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainIntent);
            }
        });
    } // onCreate(...)

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        /* Save state on exit/orientation change. */
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(Options.MIN_LENGTH, edtxtMinLength.getText().toString());
        savedInstanceState.putString(Options.MAX_LENGTH, edtxtMaxLength.getText().toString());
        savedInstanceState.putInt(Options.DIFFICULTY, rdoGrpDifficulty.getCheckedRadioButtonId());
    } // onSavedInstanceSate(...)

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        /* Restore state on exit/orientation change. */
        super.onRestoreInstanceState(savedInstanceState);

        edtxtMinLength.setText(savedInstanceState.getString(Options.MIN_LENGTH));
        edtxtMaxLength.setText(savedInstanceState.getString(Options.MAX_LENGTH));
        rdoGrpDifficulty.check(savedInstanceState.getInt(Options.DIFFICULTY));
    } // onRestoreInstanceState(...)

    protected static int getEasyRadioButtonId() {
        /* Returns the ID of the easy radio button. */
        return R.id.rdoEasy;
    } // getEasyRadioButtonId()

    private void getSharedPreferences() {
        /* Get the shared preferences, set them to the proper widgets/views. */
        SharedPreferences sharedPreferences = getSharedPreferences(Options.OPTIONS, Context.MODE_PRIVATE);

        edtxtMinLength.setText(String.valueOf(sharedPreferences.getInt(Options.MIN_LENGTH, 3)));
        edtxtMaxLength.setText(String.valueOf(sharedPreferences.getInt(Options.MAX_LENGTH, 13)));
        rdoGrpDifficulty.check(sharedPreferences.getInt(Options.DIFFICULTY, R.id.rdoEasy));
    } // getSharedPreferences()

    private void setSharedPreferences() {
        /* Set the shared preferences based on the values from the proper widgets/views. */
        SharedPreferences sharedPreferences = getSharedPreferences(Options.OPTIONS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editSharedPrefs = sharedPreferences.edit();

        editSharedPrefs.putInt(Options.MIN_LENGTH, Integer.parseInt(edtxtMinLength.getText().toString()));
        editSharedPrefs.putInt(Options.MAX_LENGTH, Integer.parseInt(edtxtMaxLength.getText().toString()));
        editSharedPrefs.putInt(Options.DIFFICULTY, rdoGrpDifficulty.getCheckedRadioButtonId());
        editSharedPrefs.apply();
    } // setSharedPreferences()
}
