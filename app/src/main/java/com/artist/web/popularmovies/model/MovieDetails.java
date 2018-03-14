package com.artist.web.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 06-Mar-18.
 */

public class MovieDetails {

    @SerializedName("id")
    private int mId;

    @SerializedName("runtime")
    private int mRuntime;

    @SerializedName("genres")
    private List<Genres> mGenres;

    @SerializedName("reviews")
    private MovieIdReviews mMovieIdReviews;

    @SerializedName("videos")
    private MovieIdVideos mMovieIdVideos;

    public MovieDetails(int id, int duration, List<Genres> genres, MovieIdReviews movieIdReviews,
                        MovieIdVideos movieIdVideos) {
        mId = id;
        mRuntime = duration;
        mGenres = genres;
        mMovieIdReviews = movieIdReviews;
        mMovieIdVideos = movieIdVideos;
    }

    public int getRuntime() {
        return mRuntime;
    }

    public void setRuntime(int duration) {
        mRuntime = duration;
    }

    public List<Genres> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genres> genres) {
        mGenres = genres;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public MovieIdReviews getMovieIdReviews() {
        return mMovieIdReviews;
    }

    public void setMovieIdReviews(MovieIdReviews movieIdReviews) {
        mMovieIdReviews = movieIdReviews;
    }

    public MovieIdVideos getMovieIdVideos() {
        return mMovieIdVideos;
    }

    public void setMovieIdVideos(MovieIdVideos movieIdVideos) {
        mMovieIdVideos = movieIdVideos;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id= " + mId +
                ",genres= " + mGenres +
                ", runtime= " + mRuntime +
                ", videos= " + mMovieIdVideos +
                ", reviews= " + mMovieIdReviews +"}";
    }
}
