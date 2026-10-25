package com.lunecode.tmdb;

import static android.provider.Contacts.SettingsColumns.KEY;
import static java.sql.Types.INTEGER;
import static java.text.Collator.PRIMARY;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE movies ( " +
                        "movieId INTEGER PRIMARY KEY," +
                        "title TEXT, " +
                        "releaseDate TEXT )"
        );

    }

    public long insertMovie(Integer movieId, String title, String releaseDate){
        SQLiteDatabase db  = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("movieId", movieId);
        cv.put("title", title);
        cv.put("releaseDate", releaseDate);

        return db.insert("movies", null, cv );

    }

    public Cursor getMovie(){
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM movies", null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
