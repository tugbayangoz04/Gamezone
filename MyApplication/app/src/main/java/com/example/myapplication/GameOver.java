package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    private TextView tvPoints, tvHighest;
    private ImageView ivNewHighest;
    private DBHelper dbHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        tvPoints = findViewById(R.id.tvPoints);
        tvHighest = findViewById(R.id.tvHighest);
        ivNewHighest = findViewById(R.id.ivNewHighest);

        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        userEmail = intent.getStringExtra("usermail");

        tvPoints.setText(String.valueOf(score));

        if (userEmail != null) {
            dbHelper.updateHighScore(userEmail, Constants.SAVE_THE_BUNNY_NAME, score);
            int maxScore = dbHelper.getHighScore(userEmail, Constants.SAVE_THE_BUNNY_NAME);
            tvHighest.setText(String.valueOf(maxScore));

            if (score >= maxScore) {
                ivNewHighest.setVisibility(View.VISIBLE);
            }
        } else {
            tvHighest.setText("-");
        }
    }

    public void restart(View view) {
        Intent intent = new Intent(GameOver.this, SaveTheBunnyMainScreen.class);
        intent.putExtra("usermail", userEmail);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        intent.putExtra("usermail", userEmail);
        startActivity(intent);
        finish();
    }
}