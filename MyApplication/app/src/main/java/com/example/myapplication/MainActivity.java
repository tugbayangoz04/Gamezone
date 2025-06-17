package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    TextView welcomeText;
    CardView memoryGameCard, moleGameCard, bunnyCard,flappyCard;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("GAMEZONE");
        }

        // View'ları bağla
        welcomeText = findViewById(R.id.welcomeText);
        memoryGameCard = findViewById(R.id.memoryGameCard);
        moleGameCard = findViewById(R.id.moleGameCard);
        bunnyCard = findViewById(R.id.bunnyCard);
        btnLogout = findViewById(R.id.btnLogout);
        flappyCard = findViewById(R.id.flappyGameCard);

        // Kullanıcı adını al ve göster
        String usermail = getIntent().getStringExtra("usermail");
        if (usermail != null) {
            welcomeText.setText("Welcome " + usermail + "!");
        } else {
            welcomeText.setText("Welcome!");
        }

        // CardView tıklamaları
        memoryGameCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MemoryCardActivity.class);
            intent.putExtra("usermail",usermail);
            startActivity(intent);
        });

        moleGameCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MoleGameActivity.class);
            intent.putExtra("usermail",usermail);
            startActivity(intent);
        });

        bunnyCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SaveTheBunnyMainScreen.class);
            intent.putExtra("usermail",usermail);
            startActivity(intent);
        });

        bunnyCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SaveTheBunnyMainScreen.class);
            intent.putExtra("usermail",usermail);
            startActivity(intent);
        });

        flappyCard.setOnClickListener(v-> {
            Intent intent = new Intent(MainActivity.this, FBMainActivity.class);
            intent.putExtra("usermail",usermail);
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
