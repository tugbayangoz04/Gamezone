package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryCardActivity extends AppCompatActivity {

    private ArrayList<CardModel> cardList = new ArrayList<>();
    private ImageView[] cardViews = new ImageView[16];
    private ImageView firstCardView = null;
    private CardModel firstCardModel = null;
    private int[] cardImageIds = {
            R.drawable.card0, R.drawable.card1, R.drawable.card2, R.drawable.card3,
            R.drawable.card4, R.drawable.card5, R.drawable.card6, R.drawable.card7
    };
    private int backImageResId = R.drawable.card_back;

    private int score = 0;
    private TextView scoreText;
    private TextView timerText;
    private CountDownTimer countDownTimer;

    private DBHelper dbHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_card);

        dbHelper = new DBHelper(this);
        userEmail = getIntent().getStringExtra("usermail");

        scoreText = findViewById(R.id.txtScore);
        timerText = findViewById(R.id.txtTime);

        initCards();
        initGrid();
        startTimer();
    }

    private void initCards() {
        for (int i = 0; i < 2; i++) {
            for (int resId : cardImageIds) {
                cardList.add(new CardModel(resId, false, false));
            }
        }
        Collections.shuffle(cardList);
    }

    private void initGrid() {
        for (int i = 0; i < 16; i++) {
            int id = getResources().getIdentifier("card" + i, "id", getPackageName());
            cardViews[i] = findViewById(id);
            cardViews[i].setImageResource(backImageResId);
            int finalI = i;
            cardViews[i].setOnClickListener(v -> onCardClicked(cardViews[finalI], finalI));
        }
    }

    private void onCardClicked(ImageView imageView, int position) {
        CardModel card = cardList.get(position);

        if (card.isMatched() || card.isOpen()) return;

        imageView.setImageResource(card.getResId());
        card.setOpen(true);

        if (firstCardView == null) {
            firstCardView = imageView;
            firstCardModel = card;
        } else {
            if (firstCardModel.getResId() == card.getResId()) {
                card.setMatched(true);
                firstCardModel.setMatched(true);
                firstCardView = null;
                firstCardModel = null;
                score += 10;
                updateScore();
            } else {
                // 🛠 Null kontrolü ile güncellendi
                new Handler().postDelayed(() -> {
                    imageView.setImageResource(backImageResId);
                    if (firstCardView != null) {
                        firstCardView.setImageResource(backImageResId);
                    }
                    card.setOpen(false);
                    if (firstCardModel != null) {
                        firstCardModel.setOpen(false);
                    }
                    firstCardView = null;
                    firstCardModel = null;
                }, 800);
            }
        }
    }


    private void updateScore() {
        scoreText.setText("Skor: " + score);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                timerText.setText("Süre: " + seconds + " sn");
            }

            public void onFinish() {
                timerText.setText("Süre doldu!");
                handleGameEnd();
            }
        }.start();
    }

    private void handleGameEnd() {
        // ⬇️ Max score veritabanından al ve güncelle
        if (userEmail != null) {
            dbHelper.updateHighScore(userEmail, Constants.MEMORY_GAME_NAME, score);
            int maxScore = dbHelper.getHighScore(userEmail, Constants.MEMORY_GAME_NAME);

            new AlertDialog.Builder(this)
                    .setTitle("Oyun Bitti")
                    .setMessage("Skorun: " + score + "\nMax Skorun: " + maxScore + "\n\nYeniden oynamak ister misin?")
                    .setPositiveButton("Yeniden Oyna", (dialog, which) -> {
                        Intent intent = getIntent();
                        intent.putExtra("usermail", userEmail);
                        finish();
                        startActivity(intent);
                    })
                    .setNegativeButton("Menüye Dön", (dialog, which) -> {
                        Intent intent = new Intent(MemoryCardActivity.this, MainActivity.class);
                        intent.putExtra("usermail",userEmail);
                        startActivity(intent);
                        finish();
                    })
                    .setCancelable(false)
                    .show();
        }
    }
}
