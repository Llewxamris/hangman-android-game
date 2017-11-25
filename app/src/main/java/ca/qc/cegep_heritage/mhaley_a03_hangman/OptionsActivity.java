package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        edtxtMinLength =  findViewById(R.id.edtxtMinLength);
        edtxtMaxLength = findViewById(R.id.edtxtMaxLength);
        rdoGrpDifficulty = findViewById(R.id.rdoGrpDifficulty);
        mainIntent = new Intent(OptionsActivity.this, MainActivity.class);

        getSharedPreferences();

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Validate Min and Max Length values before saving.
                // Min should be: 3 >= Min >= Max
                // Max should be: 3 >= Max && Min > Max

                Snackbar saveSnackbar = Snackbar
                        .make(findViewById(R.id.layOptions),"Options Saved", Snackbar.LENGTH_LONG);
                saveSnackbar.show();
                setSharedPreferences();
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

        savedInstanceState.putString("minLength", edtxtMinLength.getText().toString());
        savedInstanceState.putString("maxLength", edtxtMaxLength.getText().toString());
        savedInstanceState.putInt("selectedDifficulty", rdoGrpDifficulty.getCheckedRadioButtonId());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        edtxtMinLength.setText(savedInstanceState.getString("minLength"));
        edtxtMaxLength.setText(savedInstanceState.getString("maxLength"));
        rdoGrpDifficulty.check(savedInstanceState.getInt("selectedDifficulty"));
    }

    private void getSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("options", Context.MODE_PRIVATE);

        edtxtMinLength.setText(String.valueOf(sharedPreferences.getInt("minLength", 3)));
        edtxtMaxLength.setText(String.valueOf(sharedPreferences.getInt("maxLength", 13)));
        rdoGrpDifficulty.check(sharedPreferences.getInt("difficulty", R.id.rdoEasy));
    }

    private void setSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("options", Context.MODE_PRIVATE);
        SharedPreferences.Editor editSharedPrefs = sharedPreferences.edit();

        editSharedPrefs.putInt("minLength", Integer.parseInt(edtxtMinLength.getText().toString()));
        editSharedPrefs.putInt("maxLength", Integer.parseInt(edtxtMaxLength.getText().toString()));
        editSharedPrefs.putInt("difficulty", rdoGrpDifficulty.getCheckedRadioButtonId());
        editSharedPrefs.apply();
    }

}
