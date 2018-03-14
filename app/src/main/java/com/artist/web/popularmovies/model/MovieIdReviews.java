package com.artist.web.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 09-Mar-18.
 */

public class MovieIdReviews {

    @SerializedName("results")
    private List<MovieReviews> mMovieReviews;

    @SerializedName("page")
    private int mPage;

    @SerializedName("total_pages")
    private int mTotalPages;

    @SerializedName("total_results")
    private int mTotalResults;

    public MovieIdReviews(List<MovieReviews> movieReviews, int page, int totalPages, int totalResults) {
        mMovieReviews = movieReviews;
        mPage = page;
        mTotalPages = totalPages;
        mTotalResults = totalResults;
    }

    public List<MovieReviews> getMovieReviews() {
        return mMovieReviews;
    }

    public void setMovieReviews(List<MovieReviews> movieReviews) {
        mMovieReviews = movieReviews;
    }
}
