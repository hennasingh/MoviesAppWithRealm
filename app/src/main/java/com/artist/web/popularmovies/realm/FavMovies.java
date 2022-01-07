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
}
