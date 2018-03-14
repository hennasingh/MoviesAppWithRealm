package com.artist.web.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artist.web.popularmovies.R;
import com.artist.web.popularmovies.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 19-Feb-18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<Movies> movies;
    private final Context context;

    private final MovieAdapterOnClickListener mMovieAdapterOnClickListener;


    public interface MovieAdapterOnClickListener{
        void onItemClick(int clickedPosition);
    }

    public MovieAdapter(List<Movies> movies, MovieAdapterOnClickListener listener, Context context){
        this.movies = movies;
        mMovieAdapterOnClickListener = listener;
        this.context = context;
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                           .inflate(R.layout.card_view_list,parent,false);
        layoutView.setFocusable(true);
        return new MovieAdapterViewHolder(layoutView, mMovieAdapterOnClickListener);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        Movies movie = movies.get(position);
        holder.voteAverage.setText(String.valueOf(movie.getVoteAverage()));

         Picasso.with(context)
                .load(movie.getPosterPath())
                .into(holder.movieImage);

    }

    public void updateRecyclerData(List<Movies> newMovies) {
        this.movies = newMovies;
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
}
