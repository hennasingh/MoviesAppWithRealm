package com.artist.web.popularmovies.activity;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artist.web.popularmovies.MainApplication;
import com.artist.web.popularmovies.NetworkUtils;
import com.artist.web.popularmovies.R;
import com.artist.web.popularmovies.adapter.ReviewsAdapter;
import com.artist.web.popularmovies.adapter.VideosAdapter;
import com.artist.web.popularmovies.database.MovieListContract;
import com.artist.web.popularmovies.model.Genres;
import com.artist.web.popularmovies.model.MovieDetails;
import com.artist.web.popularmovies.model.MovieIdReviews;
import com.artist.web.popularmovies.model.MovieIdVideos;
import com.artist.web.popularmovies.model.MovieReviews;
import com.artist.web.popularmovies.model.MovieVideos;
import com.artist.web.popularmovies.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieActivity extends BaseActivity {

    private TextView mMovieHeading;
    private TextView mMovieRating;
    private TextView mMovieDate;
    private TextView mOverview;
    private ImageView mImagePoster;
    private ImageView mImageHeader;
    private TextView mMovieDuration;
    private TextView mMovieGenre;
    private Toolbar mToolbar;

    //Movie data
    private Movies mMovie;
    private  MovieDetails mMovieDetails;
    private List<MovieVideos> mMovieVideosList;
    private List<MovieReviews> mMovieReviewsList;
    private List <Genres> mGenres;
    private int duration;

    private RecyclerView mMovieTrailersRV, mMovieReviewsRV;
    private Context context = DetailMovieActivity.this;
    public static final String PARCEL_DATA = "movie_list";

    private static final String TAG = DetailMovieActivity.class.getSimpleName();

    //Adapters
    private ReviewsAdapter mReviewsAdapter;
    private VideosAdapter mVideosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mMovieHeading = findViewById(R.id.textViewMovieHeading);
        mMovieRating = findViewById(R.id.textViewRating);
        mMovieDate = findViewById(R.id.textViewDate);
        mOverview = findViewById(R.id.textViewOverview);
        mImagePoster = findViewById(R.id.imageViewPoster);
        mImageHeader = findViewById(R.id.imageViewHeader);
        mMovieDuration = findViewById(R.id.textViewDuration);
        mMovieGenre = findViewById(R.id.movie_genre);


        mMovie = getIntent().getParcelableExtra(PARCEL_DATA);

        Log.d(TAG, "data " + mMovie);
        int mMovieId = mMovie.getId();
        Log.d(TAG, "movie id " +mMovieId);


        //RV for Trailers
        mMovieTrailersRV = findViewById(R.id.recyclerView_trailers);
        RecyclerView.LayoutManager trailerLayout = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        mMovieTrailersRV.setLayoutManager(trailerLayout);

        //RV for reviews
        mMovieReviewsRV = findViewById(R.id.recyclerView_reviews);
        RecyclerView.LayoutManager reviewLayout = new LinearLayoutManager(context);
        mMovieReviewsRV.setLayoutManager(reviewLayout);

         makeNetworkRequest(mMovieId);

         displayMovieDetails();
    }

    private void makeNetworkRequest(int MovieId) {

        try{
            MainApplication.sApiClient.getMovieDetails(MovieId, NetworkUtils.API_KEY,
                    NetworkUtils.APPEND_VALUES, new Callback<MovieDetails>() {
                        @Override
                        public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                            int statusCode = response.code();
                            if(response.isSuccessful()){
                                mMovieDetails = response.body();

                                Log.d(TAG, "response " +mMovieDetails);
                                Log.d(TAG, "response body " +response.body());

                                duration = response.body().getRuntime();

                                //getting genres
                               mGenres = response.body().getGenres();

                                //getting and setting Videos
                                MovieIdVideos movieIdVideos = mMovieDetails.getMovieIdVideos();
                                mMovieVideosList = movieIdVideos.getMovieVideos();
                                mVideosAdapter = new VideosAdapter(mMovieVideosList,context);
                                mMovieTrailersRV.setAdapter(mVideosAdapter);
                                mMovieTrailersRV.setHasFixedSize(true);

                                //getting and setting Reviews
                                MovieIdReviews movieIdReviews = mMovieDetails.getMovieIdReviews();
                                mMovieReviewsList = movieIdReviews.getMovieReviews();
                                mReviewsAdapter = new ReviewsAdapter(mMovieReviewsList,context);
                                mMovieReviewsRV.setAdapter(mReviewsAdapter);
                                mMovieReviewsRV.setHasFixedSize(true);

                            }else{
                                Log.e(TAG, "Error getting successful response. Status code = " + statusCode);
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieDetails> call, Throwable t) {

                            Log.e(TAG, "Error getting response from TMDB API " + t.getMessage());
                        }
                    });

        }catch(Exception e){
            Log.e(TAG, "No Internet Connection "+ e.getMessage());
        }

    }

    private void displayMovieDetails(){

        mMovieHeading.setText(mMovie.getOriginalTitle());
        mMovieRating.setText(String.valueOf(mMovie.getVoteAverage()));
        mMovieDate.setText(mMovie.getReleaseDate());
        mOverview.setText(mMovie.getOverView());
       mMovieDuration.setText(String.valueOf(duration));

        String genres= "";
        if(mGenres!=null) {

            for (int i = 0; i < mGenres.size(); i++) {
                if (i == mGenres.size() - 1) {
                    genres = genres.concat(mGenres.get(i).getGenre());
                } else {

                    genres = genres.concat(mGenres.get(i).getGenre() + " ,");
                }
            }
        }
            mMovieGenre.setText(genres);


        Picasso.with(this)
                .load(mMovie.getBackdropPath())
                .into(mImageHeader);

        Picasso.with(this)
                .load(mMovie.getPosterPath())
                .into(mImagePoster);
    }

    public void onClickAddMovie(View view) {

        ContentValues values = new ContentValues();

        values.put(MovieListContract.MovieListEntry.COLUMN_MOVIE_NAME, mMovie.getOriginalTitle());
        values.put(MovieListContract.MovieListEntry.COLUMN_MOVIE_PLOT, mMovie.getOverView());

        Uri uri = getContentResolver().insert(MovieListContract.MovieListEntry.CONTENT_URI, values);

        if(uri!=null){
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }
    }

 }
