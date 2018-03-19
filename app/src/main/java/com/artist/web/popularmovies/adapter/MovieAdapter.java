package com.artist.web.popularmovies.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.artist.web.popularmovies.NetworkUtils;
import com.artist.web.popularmovies.R;
import com.artist.web.popularmovies.model.ColorsPalette;
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
                final MovieAdapterViewHolder movieHolder = (MovieAdapterViewHolder)holder;
                Movies movie = movies.get(position);
                movieHolder.voteAverage.setText(String.valueOf(movie.getVoteAverage()));
                String posterUrl = String.format(NetworkUtils.BASE_POSTER_URL,movie.getPosterPath());

            Picasso.with(context)
                    .load(posterUrl)
                    .fit()
                    .error(R.drawable.no_internet)
                    .into(movieHolder.movieImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable)movieHolder.movieImage.getDrawable()).getBitmap();
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    Palette.Swatch colorSwatch = ColorsPalette.getMostPopulousSwatch(palette);
                                    movieHolder.voteAverage.setBackgroundColor(colorSwatch.getRgb());
                                    movieHolder.voteAverage.setTextColor(colorSwatch.getBodyTextColor());
                                }
                            });
                        }

                        @Override
                        public void onError() {

                        }
                    });
            break;
            case FAV:
                final FavoriteAdapterViewHolder favHolder = (FavoriteAdapterViewHolder)holder;
                final Movies favMovie = movies.get(position);
                favHolder.titleView.setText(favMovie.getOriginalTitle());
                favHolder.plotView.setText(favMovie.getOverView());
                favHolder.dateView.setText(favMovie.getReleaseDate());
                favHolder.rateView.setText(String.valueOf(favMovie.getVoteAverage()));
                favHolder.mRemoveFav.setTag(favMovie.getId());

                Log.d("setting Tag ID ","=" + favMovie.getId());

                Log.d("setting Tag " ,"="+ favHolder.mRemoveFav.getTag());

                Log.d("Picture url ", favMovie.getPosterPath());
                String favPosterUrl = String.format(NetworkUtils.BASE_POSTER_URL,favMovie.getPosterPath());

                Picasso.with(context)
                        .load(favPosterUrl)
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
        if(movies==null){
            return 0;
        }
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
        ImageButton mRemoveFav;


        public FavoriteAdapterViewHolder(View itemView) {
            super(itemView);
            posterView = itemView.findViewById(R.id.imageViewPoster);
            rateView = itemView.findViewById(R.id.textViewRating);
            dateView = itemView.findViewById(R.id.textViewDate);
            plotView = itemView.findViewById(R.id.textViewOverview);
            titleView = itemView.findViewById(R.id.textViewTitle);
            mRemoveFav = itemView.findViewById(R.id.removeFromFav);

        }
    }
}
