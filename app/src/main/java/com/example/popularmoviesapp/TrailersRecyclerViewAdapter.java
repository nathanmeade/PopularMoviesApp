package com.example.popularmoviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TrailersRecyclerViewAdapter extends RecyclerView.Adapter<TrailersRecyclerViewAdapter.TrailersRecyclerViewAdapterViewHolder> {
    private ArrayList<String> mKeys;

    public TrailersRecyclerViewAdapter(ArrayList<String> keys) {
        mKeys = keys;
    }

    @NonNull
    @Override
    public TrailersRecyclerViewAdapter.TrailersRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.trailer_list_item, parent, false);
        TrailersRecyclerViewAdapterViewHolder trailerViewHolder = new TrailersRecyclerViewAdapterViewHolder(view);
        return trailerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersRecyclerViewAdapter.TrailersRecyclerViewAdapterViewHolder holder, int position) {
        holder.keyTextView.setText(mKeys.get(position));

    }

    @Override
    public int getItemCount() {
        return mKeys.size();
    }

    public class TrailersRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView keyTextView;

        public TrailersRecyclerViewAdapterViewHolder(View itemView) {
            super(itemView);
            keyTextView = itemView.findViewById(R.id.key);
        }
    }
}
