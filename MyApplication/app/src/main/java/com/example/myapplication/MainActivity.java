package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    TextView welcomeText;
    CardView memoryGameCard, moleGameCard, bunnyCard, flappyCard;
    Button btnLogout;
    String usermail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("GAMEZONE");
        }

        welcomeText = findViewById(R.id.welcomeText);
        memoryGameCard = findViewById(R.id.memoryGameCard);
        moleGameCard = findViewById(R.id.moleGameCard);
        bunnyCard = findViewById(R.id.bunnyCard);
        btnLogout = findViewById(R.id.btnLogout);
        flappyCard = findViewById(R.id.flappyGameCard);

        welcomeText.setText("Welcome!");

        usermail = getIntent().getStringExtra("usermail");

        memoryGameCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MemoryCardActivity.class);
            intent.putExtra("usermail", usermail);
            startActivity(intent);
        });

        moleGameCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MoleGameActivity.class);
            intent.putExtra("usermail", usermail);
            startActivity(intent);
        });

        bunnyCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SaveTheBunnyMainScreen.class);
            intent.putExtra("usermail", usermail);
            startActivity(intent);
        });

        flappyCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FBMainActivity.class);
            intent.putExtra("usermail", usermail);
            startActivity(intent);
        });

        // Logout butonu
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}