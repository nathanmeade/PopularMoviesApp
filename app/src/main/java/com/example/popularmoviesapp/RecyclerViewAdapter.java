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

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewAdapterViewHolder> {
    private ArrayList<String> urls;
    private final RecyclerViewAdapterOnClickHandler mClickHandler;

    public interface RecyclerViewAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    public RecyclerViewAdapter(ArrayList<String> urlsParameter, RecyclerViewAdapterOnClickHandler clickHandler) {
        urls = urlsParameter;
        mClickHandler = clickHandler;
        //url2 = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    }

    public class RecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mWeatherTextView;

        public RecyclerViewAdapterViewHolder(View view) {
            super(view);
            mWeatherTextView = (ImageView) view.findViewById(R.id.image_view);
            // COMPLETED (7) Call setOnClickListener on the view passed into the constructor (use 'this' as the OnClickListener)
            view.setOnClickListener(this);
        }

        // COMPLETED (6) Override onClick, passing the clicked day's data to mClickHandler via its onClick method
        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            //String weatherForDay = mWeatherData[adapterPosition];
            String weatherForDay = urls.get(adapterPosition);
            //weatherForDay = "test";
            mClickHandler.onClick(weatherForDay);
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

        //holder.bind(position);
        //holder.mWeatherTextView
        Picasso.get().load(urls.get(position)).into(holder.mWeatherTextView);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

/*    class MovieViewHolder extends RecyclerView.ViewHolder {
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
    }*/
}
