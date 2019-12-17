package com.example.popularmoviesapp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TrailersRecyclerViewAdapter extends RecyclerView.Adapter<TrailersRecyclerViewAdapter.TrailersRecyclerViewAdapterViewHolder> {
    private ArrayList<String> mKeys;

    private final TrailersRecyclerViewAdapter.TrailersRecyclerViewAdapterOnClickHandler mClickHandler;

    public interface TrailersRecyclerViewAdapterOnClickHandler {
        void onClick(String movieId);
    }

    TrailersRecyclerViewAdapter(ArrayList<String> keys, TrailersRecyclerViewAdapterOnClickHandler clickHandler) {
        mKeys = keys;
        mClickHandler = clickHandler;
    }

    public class TrailersRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView keyTextView;
        TrailersRecyclerViewAdapterViewHolder(View view) {
            super(view);
            keyTextView = itemView.findViewById(R.id.key);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String movieId = mKeys.get(adapterPosition);
            mClickHandler.onClick(movieId);
        }
    }

    @NonNull
    @Override
    public TrailersRecyclerViewAdapter.TrailersRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.trailer_list_item, parent, false);
        return new TrailersRecyclerViewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersRecyclerViewAdapter.TrailersRecyclerViewAdapterViewHolder holder, int position) {
        holder.keyTextView.setText("Trailer " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return mKeys.size();
    }

}
