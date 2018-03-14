package com.artist.web.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by User on 21-Feb-18.
 */

public class MovieResponse {

    @SerializedName("page")
    private int mPage;

    @SerializedName("total_results")
    private int mTotalMovies;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private ArrayList<Movies> mMovieResult;


    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public ArrayList<Movies> getResults() {
        return mMovieResult;
    }

    public void setResults(ArrayList<Movies> results) {
        this.mMovieResult = results;
    }

    public int getTotalResults() {
        return mTotalMovies;
    }

    public void setTotalResults(int totalResults) {
        mTotalMovies = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
