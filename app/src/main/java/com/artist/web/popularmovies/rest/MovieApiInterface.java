package com.artist.web.popularmovies.rest;


import com.artist.web.popularmovies.model.MovieDetails;
import com.artist.web.popularmovies.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 19-Feb-18.
 */

public interface MovieApiInterface {

      @GET("movie/{preference}")
      Call<MovieResponse> getMovies(@Path("preference") String preference,
                                         @Query("api_key") String apiKey);


      @GET("movie/{id}")
      Call<MovieDetails> getMovieDetails(@Path("id") int id,
                                           @Query("api_key") String apiKey,
                                           @Query("append_to_response") String appendValues);



}
