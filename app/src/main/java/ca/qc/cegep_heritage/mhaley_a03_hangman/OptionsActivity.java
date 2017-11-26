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

    private EditText edtxtMinLength;
    private EditText edtxtMaxLength;
    private RadioGroup rdoGrpDifficulty;
    private Intent mainIntent;
    private Button btnSave;
    private static final int MIN_WORD_LENGTH = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        edtxtMinLength =  findViewById(R.id.edtxtMinLength);
        edtxtMaxLength = findViewById(R.id.edtxtMaxLength);
        rdoGrpDifficulty = findViewById(R.id.rdoGrpDifficulty);
        btnSave = findViewById(R.id.btnSave);
        mainIntent = new Intent(OptionsActivity.this, MainActivity.class);

        getSharedPreferences();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder warningBuilder = new AlertDialog.Builder(OptionsActivity.this);
                warningBuilder.setTitle("Error");

                if (edtxtMinLength.getText().toString().equals(""))
                {
                    warningBuilder.setMessage(R.string.error_required_minLength);
                    warningBuilder.show();
                    return;
                }


                if (edtxtMaxLength.getText().toString().equals(""))
                {
                    warningBuilder.setMessage(R.string.error_required_maxLength);
                    warningBuilder.show();

                    return;
                }

                int minLength = Integer.parseInt(edtxtMinLength.getText().toString());
                int maxLength = Integer.parseInt(edtxtMaxLength.getText().toString());

                if (minLength < MIN_WORD_LENGTH) {
                    warningBuilder.setMessage(R.string.error_range_minLength);
                    warningBuilder.show();
                } else if (maxLength < minLength ) {
                    warningBuilder.setMessage(R.string.error_range_maxLength);
                    warningBuilder.show();
                } else {
                    Snackbar saveSnackbar = Snackbar
                            .make(findViewById(R.id.layOptions), R.string.options_success, Snackbar.LENGTH_LONG);
                    saveSnackbar.show();
                    setSharedPreferences();
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
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(Options.MIN_LENGTH, edtxtMinLength.getText().toString());
        savedInstanceState.putString(Options.MAX_LENGTH, edtxtMaxLength.getText().toString());
        savedInstanceState.putInt(Options.DIFFICULTY, rdoGrpDifficulty.getCheckedRadioButtonId());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        edtxtMinLength.setText(savedInstanceState.getString(Options.MIN_LENGTH));
        edtxtMaxLength.setText(savedInstanceState.getString(Options.MAX_LENGTH));
        rdoGrpDifficulty.check(savedInstanceState.getInt(Options.DIFFICULTY));
    }

    private void getSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(Options.OPTIONS, Context.MODE_PRIVATE);

        edtxtMinLength.setText(String.valueOf(sharedPreferences.getInt(Options.MIN_LENGTH, 3)));
        edtxtMaxLength.setText(String.valueOf(sharedPreferences.getInt(Options.MAX_LENGTH, 13)));
        rdoGrpDifficulty.check(sharedPreferences.getInt(Options.DIFFICULTY, R.id.rdoEasy));
    }

    protected static int getEasyRadioButtonId() {
        return R.id.rdoEasy;
    }

    private void setSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(Options.OPTIONS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editSharedPrefs = sharedPreferences.edit();

        editSharedPrefs.putInt(Options.MIN_LENGTH, Integer.parseInt(edtxtMinLength.getText().toString()));
        editSharedPrefs.putInt(Options.MAX_LENGTH, Integer.parseInt(edtxtMaxLength.getText().toString()));
        editSharedPrefs.putInt(Options.DIFFICULTY, rdoGrpDifficulty.getCheckedRadioButtonId());
        editSharedPrefs.apply();
    }
}
