package com.artist.web.popularmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 13-Mar-18.
 */

public class Genres {

    @SerializedName("name")
    private String mGenre;

    public Genres(String genreName) {
        mGenre = genreName;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }
}
