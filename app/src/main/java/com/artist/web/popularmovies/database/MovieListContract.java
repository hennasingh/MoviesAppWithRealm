package com.artist.web.popularmovies.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by User on 04-Mar-18.
 */

public class MovieListContract {

    //the code knows through authority which provider to access
    public static final String AUTHORITY = "com.artist.web.popularmovies";

    //base URI = content:// +<authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" +AUTHORITY);

    //possible paths for accessing data, this is for movies table
    public static final String PATH_MOVIES = "moviestore";

    public static final class MovieListEntry implements BaseColumns {

        //content Uri = base Uri +Path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "moviestore";

        public static final String COLUMN_MOVIE_NAME = "movieName";

        public static final String COLUMN_MOVIE_ID = "movieID";

        public static final String COLUMN_MOVIE_RATING = "movieRating";

        public static final String COLUMN_MOVIE_DATE = "movieReleaseDate";

        public static final String COLUMN_MOVIE_PLOT = "movieOverview";

        public static final String COLUMN_MOVIE_POSTER = "moviePoster";

        public static final String COLUMN_MOVIE_BACKDROP = "movieBackdrop";
    }
}
