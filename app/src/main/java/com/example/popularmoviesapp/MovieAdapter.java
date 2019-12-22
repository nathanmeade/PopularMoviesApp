package com.example.popularmoviesapp;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.popularmoviesapp.Database.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private ArrayList<Movie> mMovies;
    private final MovieAdapterOnClickHandler movieAdapterOnClickHandler;
    private RequestManager glide;
    private String baseUrl;

/*    public ArrayList<Movie> getmMovies() {
        return mMovies;
    }*/

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(ArrayList<Movie> mMovies, MovieAdapterOnClickHandler movieAdapterOnClickHandler, RequestManager glide, String baseUrl) {
        this.mMovies = mMovies;
        this.movieAdapterOnClickHandler = movieAdapterOnClickHandler;
        this.glide = glide;
        this.baseUrl = baseUrl;
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        ImageView imageView = view.findViewById(R.id.image_view);
        return new MovieAdapter.MovieAdapterViewHolder(view, imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        glide.load(baseUrl + mMovies.get(position).getPosterPath()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;

        public MovieAdapterViewHolder(@NonNull View itemView, ImageView imageView) {
            super(itemView);
            this.imageView = imageView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovies.get(adapterPosition);

/*            String posterUrl = "http://image.tmdb.org/t/p/original/or06FN3Dka5tukK1e9sl16pB3iy.jpg";
            String title = "Avengers: Endgame";
            String voteAverage = "8.5/10";
            String overview = "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.";
            String releaseDate = "2019-04-24";
            String movieId = "299534";*/

/*            String posterUrl = baseUrl + movie.getPosterPath();
            String title = movie.getName();
            String voteAverage = movie.getVoteAverage().toString();
            String overview = movie.getOverview();
            String releaseDate = movie.getReleaseDate();
            String movieId = Integer.toString(movie.getId());*/
            movieAdapterOnClickHandler.onClick(movie);
        }
    }
}
