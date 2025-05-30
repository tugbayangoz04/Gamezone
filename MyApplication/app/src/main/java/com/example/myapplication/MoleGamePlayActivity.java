package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MoleGamePlayActivity extends AppCompatActivity {
        int score = 0;
        TextView textView;
        Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
        Handler handler = new Handler();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mole_game_play);

            textView = findViewById(R.id.score);
            textView = findViewById(R.id.Time);
            b1 = findViewById(R.id.b1);
            b2 = findViewById(R.id.b2);
            b3 = findViewById(R.id.b3);
            b4 = findViewById(R.id.b4);
            b5 = findViewById(R.id.b5);
            b6 = findViewById(R.id.b6);
            b7 = findViewById(R.id.b7);
            b8 = findViewById(R.id.b8);
            b9 = findViewById(R.id.b9);

            textView.setText("Skorunuz : 0");
            hideAllButtons();

            new CountDownTimer(10000000, 2000) {
                @Override
                public void onFinish() {}

                @Override
                public void onTick(long millisUntilFinished) {
                    int ran = (int)(Math.random() * 9) + 1;

                    showOnlyButton(ran);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideAllButtons();
                        }
                    }, 800);
                }
            }.start();
        }

        private void hideAllButtons() {
            b1.setVisibility(View.INVISIBLE);
            b2.setVisibility(View.INVISIBLE);
            b3.setVisibility(View.INVISIBLE);
            b4.setVisibility(View.INVISIBLE);
            b5.setVisibility(View.INVISIBLE);
            b6.setVisibility(View.INVISIBLE);
            b7.setVisibility(View.INVISIBLE);
            b8.setVisibility(View.INVISIBLE);
            b9.setVisibility(View.INVISIBLE);
        }

        private void showOnlyButton(int index) {
            hideAllButtons();
            switch (index) {
                case 1: b1.setVisibility(View.VISIBLE); break;
                case 2: b2.setVisibility(View.VISIBLE); break;
                case 3: b3.setVisibility(View.VISIBLE); break;
                case 4: b4.setVisibility(View.VISIBLE); break;
                case 5: b5.setVisibility(View.VISIBLE); break;
                case 6: b6.setVisibility(View.VISIBLE); break;
                case 7: b7.setVisibility(View.VISIBLE); break;
                case 8: b8.setVisibility(View.VISIBLE); break;
                case 9: b9.setVisibility(View.VISIBLE); break;
            }
        }

        public void b1(View view) {
            if (b1.getVisibility() == View.VISIBLE) updateScore();
        }

        public void b2(View view) {
            if (b2.getVisibility() == View.VISIBLE) updateScore();
        }

        public void b3(View view) {
            if (b3.getVisibility() == View.VISIBLE) updateScore();
        }

        public void b4(View view) {
            if (b4.getVisibility() == View.VISIBLE) updateScore();
        }

        public void b5(View view) {
            if (b5.getVisibility() == View.VISIBLE) updateScore();
        }

        public void b6(View view) {
            if (b6.getVisibility() == View.VISIBLE) updateScore();
        }

        public void b7(View view) {
            if (b7.getVisibility() == View.VISIBLE) updateScore();
        }

        public void b8(View view) {
            if (b8.getVisibility() == View.VISIBLE) updateScore();
        }

        public void b9(View view) {
            if (b9.getVisibility() == View.VISIBLE) updateScore();
        }

        private void updateScore() {
            score++;
            textView.setText("Skorunuz : " + score);
        }
    }