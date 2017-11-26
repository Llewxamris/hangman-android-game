package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class AboutActivity extends AppCompatActivity {
    /* The about page. Shows the date/time */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView txtDateTime = findViewById(R.id.txtDateTime);
        txtDateTime.setText(new Date().toString());
    } // onCreate(...)
} // AboutActivity
