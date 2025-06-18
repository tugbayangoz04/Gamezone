package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LoginSignup.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_USERS = "users";
    private static final String COL_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";

    private static final String TABLE_SCORES = "high_scores";
    private static final String COL_GAME = "game_name";
    private static final String COL_SCORE = "score";
    private static final String COL_USER_EMAIL = "user_email";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT NOT NULL, " +
                COL_EMAIL + " TEXT NOT NULL UNIQUE, " +
                COL_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(createUserTable);

        String createScoreTable = "CREATE TABLE " + TABLE_SCORES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_EMAIL + " TEXT NOT NULL, " +
                COL_GAME + " TEXT NOT NULL, " +
                COL_SCORE + " INTEGER NOT NULL, " +
                "UNIQUE(" + COL_USER_EMAIL + ", " + COL_GAME + "))";
        db.execSQL(createScoreTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    public boolean insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (checkUserExists(email)) return false;

        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public String loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_USERNAME + " FROM " + TABLE_USERS +
                        " WHERE " + COL_EMAIL + "=? AND " + COL_PASSWORD + "=?",
                new String[]{email, password}
        );

        String usermail = null;
        if (cursor.moveToFirst()) {
            usermail = email;
        }

        cursor.close();
        return usermail;
    }

    public boolean checkUserExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_ID + " FROM " + TABLE_USERS + " WHERE " + COL_EMAIL + "=?",
                new String[]{email}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void updateHighScore(String userEmail, String gameName, int newScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " + COL_SCORE + " FROM " + TABLE_SCORES + " WHERE " + COL_USER_EMAIL + "=? AND " + COL_GAME + "=?",
                new String[]{userEmail, gameName});

        if (cursor.moveToFirst()) {
            int currentHigh = cursor.getInt(0);
            if (newScore > currentHigh) {
                ContentValues values = new ContentValues();
                values.put(COL_SCORE, newScore);
                db.update(TABLE_SCORES, values, COL_USER_EMAIL + "=? AND " + COL_GAME + "=?", new String[]{userEmail, gameName});
                Log.d("DBHelper", "High score updated for " + userEmail + " in " + gameName);
            }
        } else {
            ContentValues values = new ContentValues();
            values.put(COL_USER_EMAIL, userEmail);
            values.put(COL_GAME, gameName);
            values.put(COL_SCORE, newScore);
            db.insert(TABLE_SCORES, null, values);
            Log.d("DBHelper", "New high score inserted for " + userEmail + " in " + gameName);
        }

        cursor.close();
        db.close();
    }

    public int getHighScore(String userEmail, String gameName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_SCORE + " FROM " + TABLE_SCORES + " WHERE " + COL_USER_EMAIL + "=? AND " + COL_GAME + "=?",
                new String[]{userEmail, gameName});

        int score = 0;
        if (cursor.moveToFirst()) {
            score = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return score;
    }
}