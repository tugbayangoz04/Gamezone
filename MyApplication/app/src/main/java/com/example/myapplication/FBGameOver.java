package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FBGameOver extends AppCompatActivity {

    private TextView scoreDisplay, BestDisplay;
    private Button btnRestart, btnMore;
    private DBHelper dbHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbgame_over_screen);

        scoreDisplay = findViewById(R.id.scoreDisplay);
        BestDisplay = findViewById(R.id.BestDisplay);
        btnRestart = findViewById(R.id.btnRestart);
        btnMore = findViewById(R.id.btnMore);

        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        userEmail = intent.getStringExtra("usermail");

        scoreDisplay.setText("" + score);

        if (userEmail != null) {
            dbHelper.updateHighScore(userEmail, Constants.FLAPPY_GAME_NAME, score);
            int maxScore = dbHelper.getHighScore(userEmail, Constants.FLAPPY_GAME_NAME);
            BestDisplay.setText("" + maxScore);
        } else {
            BestDisplay.setText("-");
        }

        btnRestart.setOnClickListener(v -> {
            Intent retryIntent = new Intent(FBGameOver.this, FBGameActivity.class);
            retryIntent.putExtra("usermail", userEmail);
            startActivity(retryIntent);
            finish();
        });

        btnMore.setOnClickListener(v -> {
            Intent menuIntent = new Intent(FBGameOver.this, MainActivity.class);
            menuIntent.putExtra("usermail", userEmail);
            startActivity(menuIntent);
            finish();
        });
    }
}
