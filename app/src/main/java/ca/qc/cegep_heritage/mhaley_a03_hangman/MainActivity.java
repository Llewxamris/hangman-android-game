package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    /* The main menu of the game. Has a button to jump straight into the game, and two options menu
    items: Options, and About. Auto-sets some shared preferences if they haven't already been set.*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences(Options.OPTIONS, Context.MODE_PRIVATE);

        // Check if any preferences have already been set. If no, generate the defaults.
        if(sharedPreferences.getInt(Options.MIN_LENGTH, 0) == 0) {
            SharedPreferences.Editor editSharedPrefs = sharedPreferences.edit();
            editSharedPrefs.putInt(Options.MIN_LENGTH, 3);
            editSharedPrefs.putInt(Options.MAX_LENGTH, 13);
            editSharedPrefs.putInt(Options.DIFFICULTY, OptionsActivity.getEasyRadioButtonId());
            editSharedPrefs.apply();
        }

        Button btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the game
                Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(gameIntent);
            }
        });
    } // onCreate(...)

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } // onCreateOptionsMenu(...)

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        // Goto Options
        if (id == R.id.action_options) {
            Intent optionsIntent = new Intent(this, OptionsActivity.class);
            startActivity(optionsIntent);
        }

        // Goto About
        if (id == R.id.action_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
        }

        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected(...)
}
