package com.example.arabica;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_TABLE);

        String CREATE_FAVORITES_TABLE = "CREATE TABLE favorites (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "title TEXT, " +
                "date TEXT, " +
                "month TEXT)";
        db.execSQL(CREATE_FAVORITES_TABLE);
    }
    // Favori ekle
    public void favoriEkle(int userId, String title, String date, String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO favorites (user_id, title, date, month) VALUES (?, ?, ?, ?)",
                new Object[]{userId, title, date, month});
        db.close();
    }
    //Favori sil
    public void favoriSil(int userId, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM favorites WHERE user_id = ? AND title = ?", new Object[]{userId, title});
        db.close();
    }
    public boolean isFavori(int userId, String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM favorites WHERE user_id = ? AND title = ?",
                new String[]{String.valueOf(userId), title});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }
    //Favorileri getir
    public List<Duyuru> getFavoriler(int userId) {
        List<Duyuru> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT title, date, month FROM favorites WHERE user_id = ?",
                new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                String date = cursor.getString(1);
                String month = cursor.getString(2);
                list.add(new Duyuru(title, date, month));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // (Yükseltme yapılacaksa eski tabloyu silip yeniden oluştur)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public int getUserId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT id FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0); // id kolonu
        }

        cursor.close();
        db.close();
        return userId;
    }
    //Kullanıcı girişi kontrolü için eklendi.
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }
    // Kullanıcı bilgilerini dönen yeni method



}
