package com.artist.web.popularmovies.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.artist.web.popularmovies.R;
import com.artist.web.popularmovies.model.MovieReviews;

import java.util.List;

/**
 * Created by User on 10-Mar-18.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewAdapterViewHolder> {

   private List<MovieReviews> mMovieReviews;
    private Context context;

    public ReviewsAdapter(List<MovieReviews> reviews, Context context){
        mMovieReviews = reviews;
        this.context = context;
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.review_item_list,parent,false);

        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {

        final MovieReviews review = mMovieReviews.get(position);
        holder.mTextViewAuthor.setText(review.getAuthor());
        holder.mTextViewContent.setText(review.getContent());

        holder.mCardViewReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Movie Review");

                WebView webReview= new WebView(view.getContext());
                webReview.loadUrl(review.getUrl());
                webReview.setWebViewClient(new WebViewClient(){

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url){
                        view.loadUrl(url);
                        return true;
                    }

                });
                alert.setView(webReview);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                alert.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mMovieReviews==null){
            return 0;
        }
        return mMovieReviews.size();
    }

     class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewAuthor;
        TextView mTextViewContent;
        CardView mCardViewReviews;

         ReviewAdapterViewHolder(View itemView) {
            super(itemView);
            mTextViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            mTextViewContent = itemView.findViewById(R.id.textViewContent);
            mCardViewReviews = itemView.findViewById(R.id.cardViewReviews);
        }
    }
}
