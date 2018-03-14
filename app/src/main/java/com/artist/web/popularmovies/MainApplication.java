package com.artist.web.popularmovies;

import android.app.Application;

import com.artist.web.popularmovies.rest.ApiClient;

/**
 * Created by User on 09-Mar-18.
 */

public class MainApplication extends Application {

    public static ApiClient sApiClient;

    public void onCreate(){
        super.onCreate();
        sApiClient = ApiClient.getInstance();
    }
}
