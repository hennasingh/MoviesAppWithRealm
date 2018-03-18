package com.artist.web.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artist.web.popularmovies.R;
import com.artist.web.popularmovies.model.Movies;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 19-Feb-18.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movies> movies;
    private final Context context;
   private boolean FLAG_IS_FAV;

    private final MovieAdapterOnClickListener mMovieAdapterOnClickListener;

    private final int ALL =0;
    private final int FAV =1;


    public interface MovieAdapterOnClickListener{
        void onItemClick(int clickedPosition);
    }

    public MovieAdapter(List<Movies> movies, MovieAdapterOnClickListener listener, Context context,boolean isFav){
        this.movies = movies;
        mMovieAdapterOnClickListener = listener;
        this.context = context;
        FLAG_IS_FAV = isFav;
    }

    @Override
    public int getItemViewType(int position) {

        if (FLAG_IS_FAV){
            return FAV;
        }else
            return ALL;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder= null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch(viewType){
            case FAV:
                View fav = layoutInflater .inflate(R.layout.fav_list_item,parent,false);
                fav.setFocusable(true);
                viewHolder = new FavoriteAdapterViewHolder(fav);
                break;
            case ALL:
                View allMovies = layoutInflater.inflate(R.layout.card_view_list,parent,false);
                allMovies.setFocusable(true);
                viewHolder = new MovieAdapterViewHolder(allMovies,mMovieAdapterOnClickListener);
                break;
                }
                return viewHolder;
        }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch(holder.getItemViewType()) {

            case ALL:
                MovieAdapterViewHolder movieHolder = (MovieAdapterViewHolder)holder;
                Movies movie = movies.get(position);
                movieHolder.voteAverage.setText(String.valueOf(movie.getVoteAverage()));

            Picasso.with(context)
                    .load(String.format("https://image.tmdb.org/t/p/w342%s",movie.getPosterPath()))
                    .fit()
                    .error(R.drawable.no_internet)
                    .into(movieHolder.movieImage);
            break;
            case FAV:
                final FavoriteAdapterViewHolder favHolder = (FavoriteAdapterViewHolder)holder;
                final Movies favMovie = movies.get(position);
                favHolder.titleView.setText(favMovie.getOriginalTitle());
                favHolder.plotView.setText(favMovie.getOverView());
                favHolder.dateView.setText(favMovie.getReleaseDate());
                favHolder.rateView.setText(String.valueOf(favMovie.getVoteAverage()));

                Log.d("Picture url ", favMovie.getPosterPath());

                Picasso.with(context)
                        .load(favMovie.getPosterPath())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .error(R.drawable.no_internet)
                        .fit()
                        .into(favHolder.posterView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                               //Try again online if cache failed
                                Picasso.with(context)
                                        .load( favMovie.getPosterPath())
                                        .error(R.drawable.no_internet)
                                        .fit()
                                        .into(favHolder.posterView);
                            }
                        });

                break;

        }

    }

    public void updateRecyclerData(List<Movies> newMovies,boolean isFav) {
        this.movies = newMovies;
        FLAG_IS_FAV = isFav;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

         final ImageView movieImage;
         final TextView  voteAverage;
         final MovieAdapterOnClickListener mMovieAdapterListener;

        public MovieAdapterViewHolder(View itemView, MovieAdapterOnClickListener mMovieAdapterListener) {

            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_ImageView);
            voteAverage = itemView.findViewById(R.id.rating);
            this.mMovieAdapterListener = mMovieAdapterListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mMovieAdapterListener.onItemClick(clickedPosition);

        }
    }

    public class FavoriteAdapterViewHolder extends RecyclerView.ViewHolder{

        ImageView posterView;
        TextView rateView;
        TextView dateView;
        TextView plotView;
        TextView titleView;

        public FavoriteAdapterViewHolder(View itemView) {
            super(itemView);
            posterView = itemView.findViewById(R.id.imageViewPoster);
            rateView = itemView.findViewById(R.id.textViewRating);
            dateView = itemView.findViewById(R.id.textViewDate);
            plotView = itemView.findViewById(R.id.textViewOverview);
            titleView = itemView.findViewById(R.id.textViewTitle);
        }
    }
}
