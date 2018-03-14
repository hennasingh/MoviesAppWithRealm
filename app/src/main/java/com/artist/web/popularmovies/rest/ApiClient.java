package com.artist.web.popularmovies.rest;

import com.artist.web.popularmovies.NetworkUtils;
import com.artist.web.popularmovies.model.MovieDetails;
import com.artist.web.popularmovies.model.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 21-Feb-18.
 */

public class ApiClient {


    private static ApiClient sApiClient;
    private static MovieApiInterface sMovieApiInterface;

    private ApiClient(){

                 Retrofit mRetrofit = new Retrofit.Builder()
                    .baseUrl(NetworkUtils.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        sMovieApiInterface = mRetrofit.create(MovieApiInterface.class);
    }

    public static ApiClient getInstance(){
        if(sApiClient == null){
            sApiClient = new ApiClient();
        }
        return sApiClient;
    }

    public void getMovies(String preference, String apiKey, Callback<MovieResponse> callback){
        Call<MovieResponse> moviesCall = sMovieApiInterface.getMovies(preference,apiKey);
        moviesCall.enqueue(callback);
    }

    public void getMovieDetails(int id, String apiKey, String appendValues, Callback<MovieDetails> callback){
        Call<MovieDetails> detailsCall = sMovieApiInterface.getMovieDetails(id,apiKey,appendValues);
        detailsCall.enqueue(callback);
    }




}
