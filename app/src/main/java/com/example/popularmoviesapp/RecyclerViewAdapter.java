package com.example.popularmoviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder> {

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        //super.onBindViewHolder(holder, position, payloads);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView listItemMovieView;

        public MovieViewHolder(View view) {
            super(view);
            listItemMovieView = view.findViewById(R.id.image_view);
        }

        void bind(int movie) {
            //listItemMovieView.setImage("movie");
            //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(listItemMovieView);
            Picasso.get().load("http://image.tmdb.org/t/p/original//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(listItemMovieView);
            //https://www.google.com/url?q=http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg&sa=D&ust=1556116031164000
            //listItemMovieView.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
