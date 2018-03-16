package com.artist.web.popularmovies.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by User on 04-Mar-18.
 */

public class MoviesContentProvider extends ContentProvider {

    //integer constants for directories and single tasks
    public static final int MOVIESTORE = 100;

    public static final int MOVIE_WITH_ID = 101;

    MovieListDbHelper movieDbHelper;
    SQLiteDatabase databaseReadable, databaseWritable;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    //static buildUriMatcher method that associates URI with their int match

    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //add matches with addUri(String authority, String path, int code)
        //directories
        uriMatcher.addURI(MovieListContract.AUTHORITY, MovieListContract.PATH_MOVIES,MOVIESTORE);

        //single movie
        uriMatcher.addURI(MovieListContract.AUTHORITY, MovieListContract.PATH_MOVIES + "/#", MOVIE_WITH_ID);

        return uriMatcher;
    }


    /* onCreate() is where you should initialize anything you’ll need to setup
   your underlying data source.
   In this case, you’re working with a SQLite database, so you’ll need to
   initialize a DbHelper to gain access to it.
    */
    @Override
    public boolean onCreate() {
        Context context = getContext();
        movieDbHelper = new MovieListDbHelper(context);
        databaseWritable = movieDbHelper.getWritableDatabase();
        databaseReadable = movieDbHelper.getReadableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri,  String[] projection,  String selection,
                        String[] selectionArgs,  String sortOrder) {

        int match = sUriMatcher.match(uri);

        Cursor returnCursor;

        switch(match){
            case MOVIESTORE:
                returnCursor = databaseReadable.query(MovieListContract.MovieListEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE_WITH_ID:
                //using selection and selectionArgs
                //URI: content://<authority>tasks/#
                String id = uri.getPathSegments().get(1);
                // Selection is the _ID column = ?, and the Selection args = the row ID from the URI
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};

                returnCursor = databaseReadable.query(MovieListContract.MovieListEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(),uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch(match){
            case MOVIESTORE:
                //inserting values into the moviesdb
                long id = databaseWritable.insert(MovieListContract.MovieListEntry.TABLE_NAME,null,contentValues);

                if(id>0){
                    //succcess
                    returnUri = ContentUris.withAppendedId(MovieListContract.MovieListEntry.CONTENT_URI, id);
                }else{
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        int rowDeleted;
        switch(match){
            case MOVIE_WITH_ID:
                //using selection and selectionArgs
                //URI: content://<authority>tasks/#
                String id = uri.getPathSegments().get(1);
                // Selection is the _ID column = ?, and the Selection args = the row ID from the URI
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};
                rowDeleted = databaseWritable.delete(MovieListContract.MovieListEntry.TABLE_NAME,
                        mSelection,
                        mSelectionArgs);
                 break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(rowDeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues,  String s, String[] strings) {
        return 0;
    }

}
