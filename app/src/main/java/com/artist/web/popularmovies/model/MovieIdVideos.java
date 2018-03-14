package com.artist.web.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 06-Mar-18.
 */

public class MovieIdVideos {


    @SerializedName("results")
    private List<MovieVideos> mMovieVideos;

    public MovieIdVideos(List<MovieVideos> movieVideos) {
        mMovieVideos = movieVideos;
    }

    public List<MovieVideos> getMovieVideos() {
        return mMovieVideos;
    }

    public void setMovieVideos(List<MovieVideos> movieVideos) {
        mMovieVideos = movieVideos;
    }
}
