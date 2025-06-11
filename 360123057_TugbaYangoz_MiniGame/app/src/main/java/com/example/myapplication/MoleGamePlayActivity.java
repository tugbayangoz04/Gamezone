package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MoleGamePlayActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    TextView textView;
    TextView textView2;
    int score;
    ImageView[] array;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mole_game_play);

        dbHelper = new DBHelper(this);

        score = 0;
        textView = findViewById(R.id.score);
        textView2 = findViewById(R.id.Time);

        array = new ImageView[]{
                findViewById(R.id.imageView1),
                findViewById(R.id.imageView2),
                findViewById(R.id.imageView3),
                findViewById(R.id.imageView4),
                findViewById(R.id.imageView5),
                findViewById(R.id.imageView6),
                findViewById(R.id.imageView7),
                findViewById(R.id.imageView8),
                findViewById(R.id.imageView9)
        };

        hideImages();

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                textView2.setText("Time " + l / 1000);
            }

            @Override
            public void onFinish() {
                textView.setText("Time Off");
                handler.removeCallbacks(runnable);

                for (ImageView image : array) {
                    image.setVisibility(View.INVISIBLE);
                }

                String userEmail = getIntent().getStringExtra("usermail");

                if (userEmail != null) {
                    dbHelper.updateHighScore(userEmail, Constants.MOLE_GAME_NAME, score);
                }

                int maxScore = dbHelper.getHighScore(userEmail, Constants.MOLE_GAME_NAME);

                AlertDialog.Builder alert = new AlertDialog.Builder(MoleGamePlayActivity.this);
                alert.setTitle("Oyun Bitti");

                String message = "Max Score: " + maxScore +
                        "\nCurrent Score: " + score +
                        "\n\nBir daha oynamak ister misin?";

                alert.setMessage(message);

                alert.setPositiveButton("Evet", (dialogInterface, i) -> {
                    Intent intent = getIntent();
                    intent.putExtra("usermail", userEmail); // usermail tekrar ekleniyor
                    finish();
                    startActivity(intent);
                });

                alert.setNegativeButton("Hayır", (dialogInterface, i) -> {
                    Intent intent = new Intent(MoleGamePlayActivity.this, MainActivity.class);
                    intent.putExtra("usermail",userEmail);
                    startActivity(intent);
                    finish();
                });

                alert.show();
            }
        }.start();
    }

    public void hideImages() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : array) {
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int j = random.nextInt(array.length);
                ImageView imageView = array[j];
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnClickListener(view -> {
                    score++;
                    textView.setText("Score: " + score);
                    imageView.setVisibility(View.INVISIBLE);
                });

                handler.postDelayed(this, 800);
            }
        };
        handler.post(runnable);
    }
}
