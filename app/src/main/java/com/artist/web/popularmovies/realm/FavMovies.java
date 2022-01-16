package com.artist.web.popularmovies.realm;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class FavMovies extends RealmObject {

    @PrimaryKey
    public ObjectId _id = new ObjectId();

    @Required
    public  String movieName = "movieName";

    @Required
    public  String movieID = "movieID";

    public  String movieRating = "movieRating";

    public  String movieReleaseDate = "movieReleaseDate";

    public  String movieOverview = "movieOverview";

    public  String moviePosterImage = "moviePoster";

    public  String movieBackdropImage = "movieBackdrop";


    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMoviePosterImage() {
        return moviePosterImage;
    }

    public void setMoviePosterImage(String moviePosterImage) {
        this.moviePosterImage = moviePosterImage;
    }

    public String getMovieBackdropImage() {
        return movieBackdropImage;
    }

    public void setMovieBackdropImage(String movieBackdropImage) {
        this.movieBackdropImage = movieBackdropImage;
    }
}
