package com.artist.web.popularmovies.realm;

import android.content.ContentValues;
import android.content.Context;

import com.artist.web.popularmovies.database.MovieListContract;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MovieDatabaseOperations {

 private final Long realmVersion = 1L;

 private Realm providesRealmInstance(){
      RealmConfiguration config = new RealmConfiguration.Builder()
             .name("moviestore.realm")
             .schemaVersion(realmVersion)
             .build();

      return Realm.getInstance(config);
 }

 public  void addMovieAsFavorite(
         final String name,
         final String movieId,
         final String movieRating,
         final String movieReleaseDate,
         final String movieOverview,
         final String moviePosterImage)
 {
      Realm realm = providesRealmInstance();
      realm.executeTransactionAsync(new Realm.Transaction() {
          @Override
          public void execute(Realm realmTransaction) {
                FavMovies movie = new FavMovies();
                movie.setMovieName(name);
                movie.setMovieID(movieId);
                movie.setMovieRating(movieRating);
                movie.setMovieReleaseDate(movieReleaseDate);
                movie.setMovieOverview(movieOverview);
                movie.setMoviePosterImage(moviePosterImage);

                realmTransaction.insert(movie);
          }
      });

      realm.close();
    }

    public void getFavoriteMovies(String movieId){

       Realm realm = providesRealmInstance();

       realm.executeTransactionAsync(new Realm.Transaction() {

           @Override
           public void execute(Realm realmTransaction) {
               RealmResults<FavMovies> movies = realmTransaction.where(FavMovies.class).findAll();
           }
       });

       realm.close();
    }

    public void deleteMovieFavorite(final String movieId) {

       Realm realm = providesRealmInstance();

       realm.executeTransactionAsync(new Realm.Transaction() {
           @Override
           public void execute(Realm realmTransaction) {
               FavMovies movie = realmTransaction.where(FavMovies.class).equalTo("movieId", movieId).findFirst();

               if (movie != null) {
                   movie.deleteFromRealm();
               }
           }
       });

       realm.close();
    }



}
