package com.artist.web.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 19-Feb-18.
 */

public class Movies implements Parcelable {

    private String mBasePath = "https://image.tmdb.org/t/p/";
    private String moviePostSize = "w342%s";
    private String movieBackDropSize = "w780%s";

    @SerializedName("id")
    private int mId;

    @SerializedName("original_title")
    private String mOriginalTitle;

    @SerializedName("vote_average")
    private double mVoteAverage;

    @SerializedName("overview")
    private String mOverView;

    @SerializedName("release_date")
    private String mReleaseDate;

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("backdrop_path")
    private String mBackdropPath;

    public Movies(Parcel input){
        mId = input.readInt();
        mOriginalTitle= input.readString();
        mOverView = input.readString();
        mVoteAverage = input.readDouble();
        mReleaseDate = input.readString();
        mBackdropPath = input.readString();
        mPosterPath = input.readString();

    }
    public Movies(String originalTitle, double voteAverage, String overView, String releaseDate,
                  String posterPath) {

        mOriginalTitle = originalTitle;
        mVoteAverage = voteAverage;
        mOverView = overView;
        mReleaseDate = releaseDate;
        mPosterPath = posterPath;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getOverView() {
        return mOverView;
    }

    public void setOverView(String overView) {
        mOverView = overView;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getPosterPath() {

        return String.format("https://image.tmdb.org/t/p/w342%s",mPosterPath);
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getBackdropPath() {

        return String.format("https://image.tmdb.org/t/p/w780%s",mBackdropPath);
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mId);
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mOverView);
        parcel.writeDouble(mVoteAverage);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mBackdropPath);
        parcel.writeString(mPosterPath);
    }

    public static final Parcelable.Creator<Movies> CREATOR
            = new Parcelable.Creator<Movies>() {
        public Movies createFromParcel(Parcel in) {

            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
