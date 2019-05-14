package com.example.popularmoviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewAdapterViewHolder> {
    private ArrayList<String> mPosterUrls;
    private ArrayList<String> mTitles;
    private ArrayList<String> mVoteAverages;
    private ArrayList<String> mOverviews;
    private ArrayList<String> mReleaseDates;
    private ArrayList<String> mMovieIds;
    private final RecyclerViewAdapterOnClickHandler mClickHandler;

    public interface RecyclerViewAdapterOnClickHandler {
        void onClick(String posterUrl, String title, String voteAverage, String overview, String releaseDate, String movieId);
    }

    public RecyclerViewAdapter(ArrayList<String> posterUrls, ArrayList<String> titles, ArrayList<String> voteAverages, ArrayList<String> overviews, ArrayList<String> releaseDates, ArrayList<String> movieIds, RecyclerViewAdapterOnClickHandler clickHandler) {
        mPosterUrls = posterUrls;
        mTitles = titles;
        mVoteAverages = voteAverages;
        mOverviews = overviews;
        mReleaseDates = releaseDates;
        mMovieIds = movieIds;
        mClickHandler = clickHandler;
    }

    public class RecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView imageView;

        public RecyclerViewAdapterViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String posterUrl = mPosterUrls.get(adapterPosition);
            String title = mTitles.get(adapterPosition);
            String voteAverage = mVoteAverages.get(adapterPosition);
            String overview = mOverviews.get(adapterPosition);
            String releaseDate = mReleaseDates.get(adapterPosition);
            String movieId = mMovieIds.get(adapterPosition);
            mClickHandler.onClick(posterUrl, title, voteAverage, overview, releaseDate, movieId);
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        RecyclerViewAdapterViewHolder movieViewHolder = new RecyclerViewAdapterViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterViewHolder holder, int position) {
        Picasso.get().load(mPosterUrls.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mPosterUrls.size();
    }
}
