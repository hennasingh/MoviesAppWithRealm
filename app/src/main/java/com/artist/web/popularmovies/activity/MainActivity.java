package com.artist.web.popularmovies.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artist.web.popularmovies.MainApplication;
import com.artist.web.popularmovies.NetworkUtils;
import com.artist.web.popularmovies.R;
import com.artist.web.popularmovies.adapter.MovieAdapter;
import com.artist.web.popularmovies.model.MovieResponse;
import com.artist.web.popularmovies.model.Movies;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity
        implements MovieAdapter.MovieAdapterOnClickListener,
        NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG =MainActivity.class.getSimpleName();
    private static final String STATE_MOVIES ="state_movies" ;
    public static ArrayList<Movies> movieList;
    private final Context context = MainActivity.this;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private Toolbar mToolbar;
    private ProgressBar loadingBar;
    private TextView showMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        loadingBar = findViewById(R.id.loading_bar);
        showMessage = findViewById(R.id.show_message);

        mDrawerLayout =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout,mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        /**
         * Initialize Recycler View for setting movie images in a grid
         */
        mRecyclerView = findViewById(R.id.rv_movies);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,setGridAttributes());

         mRecyclerView.setLayoutManager(layoutManager);

         if(savedInstanceState!=null){

             movieList=savedInstanceState.getParcelableArrayList(STATE_MOVIES);
             if(mMovieAdapter!=null) {
                 mMovieAdapter.updateRecyclerData(movieList);
                 mMovieAdapter.notifyDataSetChanged();
             }else{
                 mMovieAdapter = new MovieAdapter(movieList, MainActivity.this, context);
                 mRecyclerView.setAdapter(mMovieAdapter);
                 mRecyclerView.setHasFixedSize(true);
             }

         }else {
             displayMovies("top_rated");
         }


    }

    private int setGridAttributes() {
        int size = 0;
        switch(MainActivity.this.getResources().getConfiguration().orientation){
            case Configuration.ORIENTATION_PORTRAIT:
                size= 2;
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                size= 4;
                break;
        }
        return size;
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onItemClick(int clickedPosition) {

        Intent detailIntent = new Intent(this, DetailMovieActivity.class);
        Movies movie = movieList.get(clickedPosition);
        detailIntent.putExtra(DetailMovieActivity.PARCEL_DATA , movie);
        startActivity(detailIntent);
    }

    private void displayMovies(final String preference) {

        if (TextUtils.isEmpty(NetworkUtils.API_KEY)) {
            showErrorMessage(" Please enter a valid API KEY");
            return;
        }
        try {

            if (preference.equals("top_rated")) {
                mToolbar.setTitle("Top Rated Movies");
            } else if (preference.equals("now_playing")) {
                mToolbar.setTitle("Now Playing Movies");
            } else if (preference.equals("popular")) {
                mToolbar.setTitle("Popular Movies");
            }else if(preference.equals("upcoming")){
                mToolbar.setTitle("Upcoming Movies");
            }else if(preference.equals("latest")){
                mToolbar.setTitle("Latest Movies");
            }

            MainApplication.sApiClient.getMovies(preference, NetworkUtils.API_KEY, new Callback<MovieResponse>(){

               @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        loadingBar.setVisibility(View.INVISIBLE);
                        movieList = response.body().getResults();
                        Log.d(TAG, "Number of Movies Received: " + movieList.size());
                        if (mMovieAdapter == null) {
                            mMovieAdapter = new MovieAdapter(movieList, MainActivity.this, context);
                            mRecyclerView.setAdapter(mMovieAdapter);
                            mRecyclerView.setHasFixedSize(true);

                        } else {
                            mMovieAdapter.updateRecyclerData(movieList);
                            mMovieAdapter.notifyDataSetChanged();
                        }
                    } else {

                        showErrorMessage("Failed to Load list of " + preference + " Status Code=" + statusCode);
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                    Log.e(TAG, "HTTP Call Failed " + t.getMessage());
                    showErrorMessage("HTTP Call got failed " + t.getMessage());
                }
            });
        }catch(Exception e){

            Log.e(TAG, "No Internet Connection "+ e.getMessage());
            showErrorMessage("No Internet Connection");
        }

    }

    private void showErrorMessage( String msg) {
        loadingBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        showMessage.setText(msg);
        showMessage.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.top_rated:
               displayMovies("top_rated");
                break;
            case R.id.popular:
               displayMovies("popular");
                break;
            case R.id.now_playing:
                displayMovies("now_playing");
                break;
            case R.id.upcoming:
                displayMovies("upcoming");
                break;
            case R.id.fav_movies:

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES, movieList);
    }
}
