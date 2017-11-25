package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences("options", Context.MODE_PRIVATE);

        if(sharedPreferences.getInt("minLength", 0) == 0) {
            SharedPreferences.Editor editSharedPrefs = sharedPreferences.edit();
            editSharedPrefs.putInt("minLength", 3);
            editSharedPrefs.putInt("maxLength", 13);
            editSharedPrefs.putInt("difficulty", R.id.rdoEasy); // rdoEasy is from activity_options
            editSharedPrefs.apply();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_options) {
            Intent optionsIntent = new Intent(this, OptionsActivity.class);
            startActivity(optionsIntent);
        }


        if (id == R.id.action_about) {
            Intent aboutIntent = null;
            startActivity(aboutIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
