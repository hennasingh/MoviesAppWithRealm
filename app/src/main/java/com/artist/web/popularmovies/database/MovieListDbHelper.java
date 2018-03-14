package com.artist.web.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 04-Mar-18.
 */

public class MovieListDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="movies.db";

    private static final int DATABASE_VERSION =1;

    public MovieListDbHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION); // null -> cursor factory
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_MOVIELIST_TABLE = "CREATE TABLE " +
                MovieListContract.MovieListEntry.TABLE_NAME + " (" +
                MovieListContract.MovieListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieListContract.MovieListEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                MovieListContract.MovieListEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL," +
                MovieListContract.MovieListEntry.COLUMN_MOVIE_DATE + " TEXT," +
                MovieListContract.MovieListEntry.COLUMN_MOVIE_RATING + " REAL," +
                MovieListContract.MovieListEntry.COLUMN_MOVIE_PLOT + " TEXT," +
                MovieListContract.MovieListEntry.COLUMN_MOVIE_POSTER + " TEXT," +
                MovieListContract.MovieListEntry.COLUMN_MOVIE_BACKDROP + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(CREATE_MOVIELIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ MovieListContract.MovieListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
