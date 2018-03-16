package com.artist.web.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by User on 15-Mar-18.
 */

public class Favorites   {


    private static final String TAG = Favorites.class.getSimpleName();


    public static boolean isMovieFavorite(Context context, Integer movieId) {
        if (movieId == null) return false;

        boolean isFav;
        Uri fetchFavUri = MovieListContract.MovieListEntry.CONTENT_URI;
        String stringId = Integer.toString(movieId);
        fetchFavUri = fetchFavUri.buildUpon().appendPath(stringId).build();

        Cursor retCursor =context.getContentResolver().query(fetchFavUri,
                null,
                null,
                null,
                null);

        if(retCursor.getCount()==1)
            isFav = true;
        else
            isFav = false;

        return isFav;

    }

    public static void addMovieAsFavorite(ContentValues values, Context context) {

        context.getContentResolver().insert(MovieListContract.MovieListEntry.CONTENT_URI, values);

      }
    public static void removeMovieFromFavorite(Context context, Integer movieId) {

        Uri removeFavUri = MovieListContract.MovieListEntry.CONTENT_URI;
        String stringId = Integer.toString(movieId);
        removeFavUri = removeFavUri.buildUpon().appendPath(stringId).build();
        context.getContentResolver().delete(removeFavUri,null,null);

      }


}
