package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar saveSnackbar = Snackbar
                                            .make(findViewById(R.id.layOptions),
                                                    "Options Saved",
                                                    Snackbar.LENGTH_LONG);
                saveSnackbar.show();
            }
        });

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(OptionsActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        edtxtMinLength = findViewById(R.id.edtxtMinLength);
        edtxtMaxLength = findViewById(R.id.edtxtMaxLength);
        rdoGrpDifficulty = findViewById(R.id.rdoGrpDifficulty);

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
}
