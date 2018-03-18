package com.artist.web.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artist.web.popularmovies.NetworkUtils;
import com.artist.web.popularmovies.R;
import com.artist.web.popularmovies.model.MovieVideos;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

/**
 * Created by User on 10-Mar-18.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoAdapterViewHolder>{


    private List<MovieVideos> mMovieVideos;
    private Context context;


    public VideosAdapter(List<MovieVideos> movieVideos, Context context) {
        mMovieVideos = movieVideos;
        this.context = context;
    }

    @Override
    public VideoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_list,parent,false);

        return new VideoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapterViewHolder holder, int position) {

        final MovieVideos video = mMovieVideos.get(position);

        if(video==null){
            holder.noVideos.setText(R.string.no_videos_label);
            return;
        }


        holder.mThumbnailView.initialize(NetworkUtils.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView,
                                                final YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(video.getKey());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailView.setVisibility(View.VISIBLE);
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView,
                                                 YouTubeThumbnailLoader.ErrorReason errorReason) {
                        youTubeThumbnailLoader.release();
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView,
                                                YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        //set the click listener to play the video
        holder.mPlayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity)context,
                        NetworkUtils.YOUTUBE_API_KEY, video.getKey());
                context.startActivity(intent);

            }
        });

        holder.mShareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Watch Movie Trailer on You Tube");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + video.getKey());
                view.getContext().startActivity(shareIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieVideos.size();
    }


    public class VideoAdapterViewHolder extends RecyclerView.ViewHolder {


        ImageView mShareImageView;
        ImageView mPlayImageView;
        YouTubeThumbnailView mThumbnailView;
        TextView noVideos;

        public VideoAdapterViewHolder(View itemView) {
            super(itemView);
            mShareImageView = itemView.findViewById(R.id.imageViewShare);
            mPlayImageView = itemView.findViewById(R.id.youTubePlay);
            mThumbnailView = itemView.findViewById(R.id.videoThumbnailView);
            noVideos = itemView.findViewById(R.id.noVideos);
        }

     }
}
