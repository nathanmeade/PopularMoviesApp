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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder> {
    private ArrayList<String> urls;
    public RecyclerViewAdapter(ArrayList<String> urlsParameter) {
        urls = urlsParameter;
        //url2 = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    }

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
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView listItemMovieView;

        public MovieViewHolder(View view) {
            super(view);
            listItemMovieView = view.findViewById(R.id.image_view);
        }

        void bind(int movie) {
            //https://www.google.com/url?q=http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg&sa=D&ust=1556472191160000
            //Picasso.get().load(url2).into(listItemMovieView);
            Picasso.get().load(urls.get(movie)).into(listItemMovieView);
            //Picasso.get().load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(listItemMovieView);
        }
    }
}
