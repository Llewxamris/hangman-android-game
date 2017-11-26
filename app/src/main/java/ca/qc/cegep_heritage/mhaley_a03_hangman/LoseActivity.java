package ca.qc.cegep_heritage.mhaley_a03_hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        TextView txtActualWord = findViewById(R.id.txtActualWord);
        txtActualWord.setText(getIntent().getStringExtra("actualWord"));

        Button btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(LoseActivity.this, GameActivity.class);
                startActivity(gameIntent);
            }
        });
    }
}
