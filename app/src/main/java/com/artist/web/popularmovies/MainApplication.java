package com.artist.web.popularmovies;

import android.app.Application;

import com.artist.web.popularmovies.rest.ApiClient;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

/**
 * Created by User on 09-Mar-18.
 */

public class MainApplication extends Application {

    public static ApiClient sApiClient;

    public void onCreate(){
        super.onCreate();
        sApiClient = ApiClient.getInstance();
        Realm.init(this);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

    }
}
